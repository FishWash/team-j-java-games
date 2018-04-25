package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank extends CollidableGameObject implements ClockObserver, Damageable
{
  private final double MOVE_SPEED = 3;
  private final double TURN_SPEED = 3;
  private final double SHOOT_OFFSET = 25;
  private final double COLLIDER_SIZE = 48;

  private int health = 100;
  private MultiSprite multiSprite;
  private TankKeyInput tankKeyInput;

  private Weapon defaultWeapon, equippedWeapon;

  public Tank(Vector2 position, GameWorld.Player owner) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
    multiSprite = new MultiSprite(GameWorld.loadSprite("Tank_blue_base_strip60.png"), 60);
    trigger.setSize(new Vector2(48, 48));

    defaultWeapon = new ShellWeapon();

    this.owner = owner;
    tankKeyInput = new FuzzyTankKeyInput(owner);
    if (owner == GameWorld.Player.Two) {
      rotation = 180;
    }
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
    moveVector = GameWorld.getMoveVectorWithCollision(trigger, moveVector);
    position = Vector2.addVectors(position, moveVector);
  }

  private void shoot() {
    if (tankKeyInput.getShootPressed()) {
      Vector2 offsetPosition = Vector2.newRotationMagnitudeVector(rotation, SHOOT_OFFSET);
      Vector2 bulletPosition = Vector2.addVectors(this.getCenterPosition(), offsetPosition);

      Weapon weapon;
      if (equippedWeapon != null) {
        weapon = equippedWeapon;
      }
      else {
        weapon = defaultWeapon;
      }

      boolean shotFired = weapon.fire(bulletPosition, rotation, owner);
      if (shotFired && tankKeyInput instanceof FuzzyTankKeyInput) {
        ((FuzzyTankKeyInput) tankKeyInput).addFuzzedMoveInput(weapon.getRecoil());
      }
    }
  }

  public void takeDamage(int damage) {
    health -= damage;
    System.out.println(health);
    if (health <= 0)
      die();
  }

  @Override
  public Vector2 getCenterPosition() {
    return new Vector2(position.x + (COLLIDER_SIZE/2), position.y + (COLLIDER_SIZE/2));
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
    double spriteOffset = (COLLIDER_SIZE - sprite.getWidth())/2;
    graphics.drawImage(sprite, (int)(position.x + spriteOffset), (int)(position.y + spriteOffset), null);
  }
}
