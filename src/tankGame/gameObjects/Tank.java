package tankGame.gameObjects;

import general.gameObjects.BoxCollidableGameObject;
import general.gameObjects.BoxTrigger;
import general.gameObjects.CenterBoxTrigger;
import general.gameObjects.Damageable;
import tankGame.FuzzyTankKeyInput;
import tankGame.TankGameWorld;
import tankGame.TankKeyInput;
import tankGame.gameObjects.pickups.Pickup;
import tankGame.weapons.ShellWeapon;
import tankGame.weapons.Weapon;
import utility.ClockListener;
import utility.MultiSprite;
import utility.Timer;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tank extends BoxCollidableGameObject implements ClockListener, Damageable
{
  private final double MOVE_SPEED = 3;
  private final double TURN_SPEED = 3.5;
  private final double SHOOT_OFFSET = 25;
  private final double COLLIDER_SIZE = 48;

  private int maxHealth = 100;
  private int health = maxHealth;
  private MultiSprite multiSprite;
  private TankKeyInput tankKeyInput;

  private Weapon defaultWeapon, equippedWeapon;

  private Timer speedBoostTimer = new Timer();

  public Tank(Vector2 position, TankGameWorld.Player owner) {
    super(position);
    multiSprite = new MultiSprite(TankGameWorld.getInstance().loadSprite("Tank_blue_base_strip60.png"), 60);
    sprite = multiSprite.getSubSprite(0);
    trigger = new CenterBoxTrigger(this, new Vector2(COLLIDER_SIZE, COLLIDER_SIZE));
    this.owner = owner;
    if (owner == TankGameWorld.Player.One) {
      setSprite("Tank_blue_basic_strip60.png");
    }
    else if (owner == TankGameWorld.Player.Two) {
      setSprite("Tank_red_basic_strip60.png");
      rotation = 180;
    }
    tankKeyInput = new FuzzyTankKeyInput(owner);

    renderingLayer = TankGameWorld.RenderingLayer.Tanks;

    defaultWeapon = new ShellWeapon();
//    equippedWeapon = new NukeWeapon();

    TankGameWorld.getInstance().loadSound("tankhit.wav");
    TankGameWorld.getInstance().loadSound("BIGEXPLOSION.wav");
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public int getHealth(){
    return health;
  }

  public MultiSprite getMultiSprite() {
    return multiSprite;
  }

  public Weapon getWeapon() {
    if(equippedWeapon != null){
      return equippedWeapon;
    }
    return defaultWeapon;
  }

  public void update() {
    if (alive) {
      turn();
      move();
      shoot();
      pickup();
    }
  }

  private void turn() {
    double rotationalVelocity = tankKeyInput.getTurnInput()*TURN_SPEED;
    if (!speedBoostTimer.isDone()) {
      rotationalVelocity *= 1.2;
    }
    rotation = (rotation+360 - rotationalVelocity) % 360;
  }

  private void move() {
    double newMoveVectorMagnitude = tankKeyInput.getMoveInput()*MOVE_SPEED;
    if (!speedBoostTimer.isDone()) {
      newMoveVectorMagnitude *= 1.3;
    }
    Vector2 moveVector = Vector2.newRotationMagnitudeVector(rotation, newMoveVectorMagnitude);
    moveVector = TankGameWorld.getInstance().getMoveVectorWithCollision((BoxTrigger)trigger, moveVector);
    position = Vector2.addVectors(position, moveVector);
  }

  private void shoot() {
    if (tankKeyInput.getShootPressed()) {
      Vector2 offsetPosition = Vector2.newRotationMagnitudeVector(this.rotation, SHOOT_OFFSET);
      Vector2 projectilePosition = Vector2.addVectors(this.position, offsetPosition);

      double recoil = 0;
      if (equippedWeapon != null) {
        if (equippedWeapon.fire(projectilePosition, rotation, owner)) {
          recoil = equippedWeapon.getRecoil();
        }
        if (equippedWeapon.getAmmo() <= 0) {
          equippedWeapon = null;
        }
      }
      else if (defaultWeapon != null) {
        if (defaultWeapon.fire(projectilePosition, rotation, owner)) {
          recoil = defaultWeapon.getRecoil();
        }
      }

      if (tankKeyInput instanceof FuzzyTankKeyInput) {
        ((FuzzyTankKeyInput) tankKeyInput).addFuzzedMoveInput(recoil);
      }
    }
  }

  private void pickup() {
    CopyOnWriteArrayList<Pickup> pickups = TankGameWorld.getInstance().getPickups();
    for (Pickup pickup : pickups) {
      if (pickup.getTrigger().isOverlapping(trigger)) {
        pickup.activatePickup(this);
      }
    }
  }

  public void equip(Weapon weapon) {
    equippedWeapon = weapon;
  }

  public void damage(int damageAmount) {
    health -= damageAmount;
    TankGameWorld.getInstance().playSound("tankhit.wav");
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

  public void giveSpeedBoost(int durationInFrames) {
    speedBoostTimer.set(durationInFrames);
  }

  @Override
  public void die() {
    TankGameWorld.getInstance().instantiate(new TankExplosion(position));
    super.die();
  }

  // Sprite stuff
  @Override
  public void setSprite(String fileName) {
    BufferedImage spriteStrip = TankGameWorld.getInstance().loadSprite(fileName);
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
