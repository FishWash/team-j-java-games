package galacticMail.gameObjects;

import galacticMail.GalacticMailWorld;
import galacticMail.PointsHandler;
import galacticMail.RocketKeyInput;
import general.GameWorld;
import utility.MultiSprite;
import utility.Timer;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Rocket extends SpaceObject {
  private final double MOVE_SPEED = 0.05;
  private final double TURN_SPEED = 0.5;
  private final double MAX_VELOCITY = 4;
  private final int LANDING_TIME = 24;
  private final int INVULNERABILITY_TIME = 24;

  private GalacticMailWorld galacticMailWorld;

  private Vector2 velocityVector = new Vector2(0, 0);
  private RocketKeyInput rocketKeyInput;
  private MultiSprite flyingMultiSprite;
  private MultiSprite landedMultiSprite;
  private Moon dockedMoon = null;

  private Timer landingTimer = new Timer();
  private Timer invulnerabilityTimer = new Timer();

  public Rocket(Vector2 position) {
    super(position, 24);
    setRotation(90);
    setPosition(Vector2.addVectors(position, new Vector2(0, -16)));

    rocketKeyInput = new RocketKeyInput();

    if (GameWorld.getInstance() instanceof GalacticMailWorld) {
      galacticMailWorld = (GalacticMailWorld)GameWorld.getInstance();
    }

    Moon myMoon = (Moon)GameWorld.getInstance()
            .instantiate(new EmptyMoon(position));
    dock(myMoon);

    BufferedImage spriteStrip = GameWorld.getInstance()
            .loadSprite("Flying_strip72.png");
    flyingMultiSprite = new MultiSprite(spriteStrip, 72);
    spriteStrip = GameWorld.getInstance()
            .loadSprite("Landed_strip72.png");
    landedMultiSprite = new MultiSprite(spriteStrip, 72);
    GameWorld.getInstance().loadSprite("Explosion_space_strip9.png");
    renderingLayerIndex = 3;
  }

  @Override
  public void update() {
    turn();
    move();
    launch();
    checkCollisions();
    losePoints();
  }

  private void turn() {
    double rotationalVelocity = rocketKeyInput.getTurnInput()*TURN_SPEED;
    if (dockedMoon != null) {
      rotationalVelocity *= 8;
    }
    setRotation(rotation - rotationalVelocity);
  }

  private void move() {
    if (dockedMoon == null) {
      Vector2 newMoveVector = Vector2.newRotationMagnitudeVector(rotation, MOVE_SPEED);
      velocityVector = Vector2.addVectors(velocityVector, newMoveVector);
      velocityVector.clampMagnitude(MAX_VELOCITY);
      setPosition(Vector2.addVectors(position, velocityVector));
    }
    else {
      velocityVector = new Vector2(0, 0);
      if (!landingTimer.isDone()) {
        Vector2 moveVector = Vector2.getMoveVectorOverTime(position,
                dockedMoon.getPosition(), landingTimer.getTime());
        setPosition(Vector2.addVectors(position, moveVector));
      }
      else {
        setPosition(dockedMoon.getPosition());
      }
    }
  }

  private void launch() {
    if (rocketKeyInput.getLaunchPressed() && dockedMoon != null
            && landingTimer.isDone()) {
      dockedMoon.destroy();
      dockedMoon = null;
      velocityVector = Vector2.newRotationMagnitudeVector(rotation, MOVE_SPEED*32);
      invulnerabilityTimer.set(INVULNERABILITY_TIME);
    }
  }

  private void checkCollisions() {
    GameWorld gameWorld = GameWorld.getInstance();
    if (alive && gameWorld instanceof GalacticMailWorld) {
      GalacticMailWorld galacticMailWorld = (GalacticMailWorld)gameWorld;

      if (dockedMoon == null) {
        CopyOnWriteArrayList<Moon> moons = galacticMailWorld.getMoons();
        for (Moon moon : moons) {
          if (trigger.isOverlapping(moon.getTrigger())) {
            dock(moon);
          }
        }
      }
      if (dockedMoon == null && invulnerabilityTimer.isDone()) {
        CopyOnWriteArrayList<Asteroid> asteroids = galacticMailWorld.getAsteroids();
        for (Asteroid asteroid : asteroids) {
          if (trigger.isOverlapping(asteroid.getTrigger())) {
            GameWorld.getInstance().instantiate(new Explosion(position, rotation));
            die();
          }
        }
      }

    }
  }

  // Lose points if rocket is docked, but not if game is over.
  private void losePoints() {
    if (dockedMoon != null && !galacticMailWorld.isGameOver()) {
      PointsHandler.getInstance().losePoints(5);
    }
  }

  private void dock(Moon moon) {
    dockedMoon = moon;
    PointsHandler.getInstance().addPoints(((GalacticMailWorld)GalacticMailWorld.getInstance()).pointsToAdd);
    landingTimer.set(LANDING_TIME);

    // win condition
    if(((GalacticMailWorld) GalacticMailWorld.getInstance()).getMoons().size() == 1){
      ((GalacticMailWorld) GalacticMailWorld.getInstance()).setGameState(GalacticMailWorld.GameState.Victory);
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    if (dockedMoon == null || !landingTimer.isDone()) {
      sprite = flyingMultiSprite.getSubSpriteByRotation(rotation);
    }
    else {
      sprite = landedMultiSprite.getSubSpriteByRotation(rotation);
    }
    super.drawSprite(graphics);
  }

  @Override
  public void die(){
    super.die();
    ((GalacticMailWorld) GalacticMailWorld.getInstance()).setGameState(GalacticMailWorld.GameState.Defeat);
  }
}
