package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank extends CollidableGameObject implements ClockObserver, Damageable
{
  public enum Player {One, Two}

  private final double MOVE_SPEED = 3;
  private final double TURN_SPEED = 2;
  private final double SHOOT_OFFSET = 30;
  private final int SHOOT_DELAY = 30;

  private int health = 100;
  private Vector2 moveVector = new Vector2(0, 0);
  private double rotation = 0; //rotation in angles. (0-360)

  private MultiSprite multiSprite;
  private TankKeyInput tankKeyInput;
  private Timer shootTimer;

  public Tank(Player player, Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
    multiSprite = new MultiSprite(GameWorld.loadSprite("Tank_blue_base_strip60.png"), 60);
    collider.setSize(new Vector2(sprite.getWidth(), sprite.getHeight()));
    shootTimer = new Timer();

    tankKeyInput = new FuzzyTankKeyInput(player);
  }

  public void update() {
    turn();
    move();
    shoot();
  }

  private void turn() {
    double rotationalVelocity = tankKeyInput.getTurnInput()*TURN_SPEED;
    rotation = (rotation+360 - rotationalVelocity) % 360;
  }

  private void move() {
    double newMoveVectorMagnitude = tankKeyInput.getMoveInput()*MOVE_SPEED;
    moveVector = Vector2.newRotationMagnitudeVector(rotation, newMoveVectorMagnitude);
    position = Vector2.addVectors(position, moveVector);
    GameWorld.checkCollisions(collider);
  }

  private void shoot() {
    if (tankKeyInput.getShootPressed() && shootTimer.isDone()) {
      Vector2 offsetPosition = Vector2.newRotationMagnitudeVector(rotation, SHOOT_OFFSET);
      Vector2 bulletPosition = Vector2.addVectors(this.getCenterPosition(), offsetPosition);
      Bullet bullet = (Bullet) GameWorld.instantiate( new Bullet(bulletPosition) );
      bullet.setRotation(rotation);
      shootTimer.set(SHOOT_DELAY);

      // recoil
      if (tankKeyInput instanceof FuzzyTankKeyInput) {
        ((FuzzyTankKeyInput) tankKeyInput).addFuzzedMoveInput(-0.8);
      }
    }
  }

  public void takeDamage(int damage) {
    health -= damage;
    if (health <= 0)
      die();
  }



  private void die() {
    // do die stuff
  }

  // Sprite stuff
  @Override
  public void setSprite(String fileName)
  {
    BufferedImage sprite = GameWorld.loadSprite(fileName);
    if (sprite != null) {
      multiSprite.setSpriteStrip(sprite);
    }
  }

  @Override
  public void drawSprite(Graphics graphics)
  {
    BufferedImage sprite = multiSprite.getSubSpriteByRotation(rotation);
    graphics.drawImage(sprite, (int)position.x, (int)position.y, null);
  }
}
