package galacticMail.gameObjects;

import galacticMail.GalacticMailWorld;
import galacticMail.PointsHandler;
import galacticMail.RocketKeyInput;
import general.GameWorld;
import utility.MultiSprite;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Rocket extends SpaceObject {
  private final double MOVE_SPEED = 0.05;
  private final double MAX_VELOCITY = 4;
  private final double TURN_SPEED = 0.5;
  private Vector2 velocityVector = new Vector2(0, 0);
  private RocketKeyInput rocketKeyInput;
  private MultiSprite multiSprite;
  private Moon dockedMoon = null;

  public Rocket(Vector2 position) {
    super(position, 24);
    rocketKeyInput = new RocketKeyInput();
    BufferedImage spriteStrip = GameWorld.getInstance()
            .loadSprite("Flying_strip72.png");
    multiSprite = new MultiSprite(spriteStrip, 72);
    renderingLayerIndex = 3;
  }

  @Override
  public void update() {
    turn();
    move();
    launch();
    checkCollisions();
    PointsHandler.getInstance().losePoints();
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
      setPosition(dockedMoon.getPosition());
      velocityVector = new Vector2(0, 0);
    }
  }

  private void launch() {
    if (rocketKeyInput.getShootPressed() && dockedMoon != null) {
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
        for (Asteroid asteroid : asteroids) {
          if (trigger.isOverlapping(asteroid.getTrigger())) {
            die();
          }
        }
      }
    }
  }

  private void dock(Moon moon) {
    dockedMoon = moon;
    PointsHandler.getInstance().addPoints(((GalacticMailWorld) GalacticMailWorld.getInstance()).pointsToAdd);

    if(((GalacticMailWorld) GalacticMailWorld.getInstance()).getMoons().size() == 1){
      ((GalacticMailWorld) GalacticMailWorld.getInstance()).setGameState(GalacticMailWorld.GameState.Victory);
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = multiSprite.getSubSpriteByRotation(rotation);
    super.drawSprite(graphics);
  }

  @Override
  public void die(){
    super.die();
    ((GalacticMailWorld) GalacticMailWorld.getInstance()).setGameState(GalacticMailWorld.GameState.Lost);
  }
}
