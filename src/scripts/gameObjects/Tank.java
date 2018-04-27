package scripts.gameObjects;

import scripts.Damageable;
import scripts.GameWorld;
import scripts.utility.TankKeyInput;
import scripts.utility.ClockListener;
import scripts.utility.FuzzyTankKeyInput;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;
import scripts.weapons.MachineGunWeapon;
import scripts.weapons.ShellWeapon;
import scripts.weapons.Weapon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank extends BoxCollidableGameObject implements ClockListener, Damageable
{
  private final double MOVE_SPEED = 3;
  private final double TURN_SPEED = 3;
  private final double SHOOT_OFFSET = 25;
  private final double COLLIDER_SIZE = 48;

  private int maxHealth = 100;
  private int health = maxHealth;
  private MultiSprite multiSprite;
  private TankKeyInput tankKeyInput;

  private Weapon defaultWeapon, equippedWeapon;

  public Tank(Vector2 position, GameWorld.Player owner) {
    super(position);
    multiSprite = new MultiSprite(GameWorld.getInstance().loadSprite("Tank_blue_base_strip60.png"), 60);
    sprite = multiSprite.getSubSprite(0);
    boxTrigger = new CenterBoxTrigger(this, new Vector2(COLLIDER_SIZE, COLLIDER_SIZE));
    this.owner = owner;
    if (owner == GameWorld.Player.One) {
      setSprite("Tank_blue_basic_strip60.png");
    }
    else if (owner == GameWorld.Player.Two) {
      setSprite("Tank_red_basic_strip60.png");
      rotation = 180;
    }
    tankKeyInput = new FuzzyTankKeyInput(owner);

    renderingLayer = GameWorld.RenderingLayer.Tanks;

    defaultWeapon = new ShellWeapon();
//    equippedWeapon = new MachineGunWeapon();

    GameWorld.getInstance().loadSound("BIGEXPLOSION.wav");
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public int getHealth(){
    return health;
  }

  public void update() {
    if (alive) {
      turn();
      move();
      shoot();
    }
  }

  private void turn() {
    double rotationalVelocity = tankKeyInput.getTurnInput()*TURN_SPEED;
    rotation = (rotation+360 - rotationalVelocity) % 360;
  }

  private void move() {
    double newMoveVectorMagnitude = tankKeyInput.getMoveInput()*MOVE_SPEED;
    Vector2 moveVector = Vector2.newRotationMagnitudeVector(rotation, newMoveVectorMagnitude);
    moveVector = GameWorld.getMoveVectorWithCollision(boxTrigger, moveVector);
    position = Vector2.addVectors(position, moveVector);
  }

  private void shoot() {
    if (tankKeyInput.getShootPressed()) {
      Vector2 offsetPosition = Vector2.newRotationMagnitudeVector(this.rotation, SHOOT_OFFSET);
      Vector2 bulletPosition = Vector2.addVectors(this.position, offsetPosition);

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
      if (equippedWeapon != null && equippedWeapon.getAmmo() <= 0) {
        equippedWeapon = null;
      }
    }
  }

  public void damage(int damageAmount) {
    health -= damageAmount;
    if (health <= 0) {
      health = 0;
      die();
    }
  }

  public void heal(int healAmount) {
    health += healAmount;
    if (health > maxHealth) {
      health = maxHealth;
    }
  }

  @Override
  public void die() {
    GameWorld.instantiate(new TankExplosion(position));
    super.die();
  }

  // Sprite stuff
  @Override
  public void setSprite(String fileName) {
    BufferedImage spriteStrip = GameWorld.getInstance().loadSprite(fileName);
    if (spriteStrip != null) {
      multiSprite.setSpriteStrip(spriteStrip);
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = multiSprite.getSubSpriteByRotation(rotation);
    super.drawSprite(graphics);
  }
}
