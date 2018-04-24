package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Tank extends CollidableGameObject implements ClockObserver, Damageable
{
  private final double MOVE_SPEED = 3;
  private final double TURN_SPEED = 3;
  private final double SHOOT_OFFSET = 25;

  private int health = 100;
  private MultiSprite multiSprite;
  private TankKeyInput tankKeyInput;

  private Item defaultItem, equippedItem;

  public Tank(Vector2 position, GameWorld.Player owner) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
    multiSprite = new MultiSprite(GameWorld.loadSprite("Tank_blue_base_strip60.png"), 60);
    trigger.setSize(new Vector2(sprite.getWidth(), sprite.getHeight()));

    defaultItem = new ShellItem();

    this.owner = owner;
    tankKeyInput = new FuzzyTankKeyInput(owner);
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
    Vector2 moveVector = Vector2.newRotationMagnitudeVector(rotation, newMoveVectorMagnitude);
    GameWorld.moveWithCollision(this, moveVector);
  }

  private void shoot() {
    if (tankKeyInput.getShootPressed()) {
      Vector2 offsetPosition = Vector2.newRotationMagnitudeVector(rotation, SHOOT_OFFSET);
      Vector2 bulletPosition = Vector2.addVectors(this.getCenterPosition(), offsetPosition);

      Item item;
      if (equippedItem != null) {
        item = equippedItem;
      }
      else {
        item = defaultItem;
      }

      boolean shotFired = item.shoot(bulletPosition, rotation, owner);
      if (shotFired && tankKeyInput instanceof FuzzyTankKeyInput) {
        ((FuzzyTankKeyInput) tankKeyInput).addFuzzedMoveInput(item.getRecoil());
      }
    }
  }

  public void takeDamage(int damage) {
    health -= damage;
    System.out.println(health);
    if (health <= 0)
      die();
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
