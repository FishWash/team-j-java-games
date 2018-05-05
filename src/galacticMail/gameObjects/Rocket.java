package galacticMail.gameObjects;

import galacticMail.GalacticMailWorld;
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
  private final int LANDING_TIME = 32;

  private Vector2 velocityVector = new Vector2(0, 0);
  private RocketKeyInput rocketKeyInput;
  private MultiSprite flyingMultiSprite;
  private MultiSprite landedMultiSprite;
  private Moon dockedMoon = null;

  private Timer landingTimer = new Timer();

  public Rocket(Vector2 position) {
    super(position, 24);
    rocketKeyInput = new RocketKeyInput();
    BufferedImage spriteStrip = GameWorld.getInstance()
            .loadSprite("Flying_strip72.png");
    flyingMultiSprite = new MultiSprite(spriteStrip, 72);
    spriteStrip = GameWorld.getInstance()
            .loadSprite("Landed_strip72.png");
    landedMultiSprite = new MultiSprite(spriteStrip, 72);
    renderingLayerIndex = 3;

    GameWorld.getInstance().loadSprite("Explosion_space_strip9.png");
  }

  @Override
  public void update() {
    turn();
    move();
    launch();
    checkCollisions();
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
    if (rocketKeyInput.getShootPressed() && dockedMoon != null
            && landingTimer.isDone()) {
      dockedMoon.destroy();
      dockedMoon = null;
      velocityVector = Vector2.newRotationMagnitudeVector(rotation, MOVE_SPEED*32);
    }
  }

  private void checkCollisions() {
    GameWorld gameWorld = GameWorld.getInstance();
    if (gameWorld instanceof GalacticMailWorld) {
      GalacticMailWorld galacticMailWorld = (GalacticMailWorld)gameWorld;

      CopyOnWriteArrayList<Moon> moons = galacticMailWorld.getMoons();
      CopyOnWriteArrayList<Asteroid> asteroids = galacticMailWorld.getAsteroids();

      if (dockedMoon == null) {
        for (Moon moon : moons) {
          if (trigger.isOverlapping(moon.getTrigger())) {
            dock(moon);
          }
        }
      }
      if (dockedMoon == null) {
        for (Asteroid asteroid : asteroids) {
          if (trigger.isOverlapping(asteroid.getTrigger())) {
            GameWorld.getInstance().instantiate(new Explosion(position, rotation));
            die();
          }
        }
      }
    }
  }

  private void dock(Moon moon) {
    dockedMoon = moon;
    landingTimer.set(LANDING_TIME);
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
}
