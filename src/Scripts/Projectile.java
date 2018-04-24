package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends TriggerGameObject implements ClockObserver
{
  private double moveSpeed = 1;
  private int damage = 1;
  private MultiSprite multiSprite;
  private Timer lifeTimer;

  public Projectile(int lifeTime, GameWorld.Player owner) {
    super();
    this.sprite = GameWorld.loadSprite("Shell_basic.png");
    this.multiSprite = new MultiSprite(GameWorld.loadSprite("Shell_basic_strip60.png"), 60);
    this.trigger = new BoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    this.lifeTimer = new Timer(lifeTime);
    this.owner = owner;
  }

  public void update() {
    checkCollisions();
    move();

    if (lifeTimer.isDone()) {
      die();
    }
  }

  private void move() {
    Vector2 moveVector = Vector2.newRotationMagnitudeVector(rotation, moveSpeed);
    position = Vector2.addVectors( position, moveVector );
  }

  protected void checkCollisions() {
    ArrayList<Damageable> overlappingDamageables = GameWorld.findOverlappingEnemyDamageable(trigger, owner);
    if (overlappingDamageables.size() > 0) {
      for (Damageable d : overlappingDamageables) {
        d.takeDamage(damage);
      }
      die();
    }
    else {
      if (GameWorld.findOverlappingWall(trigger) != null) {
        die();
      }
    }
  }

  public void setPosition(Vector2 position) {
    this.position = Vector2.subtractVectors( position, new Vector2(sprite.getWidth()/2, sprite.getHeight()/2 ));
  }
  public void setRotation(double angle) {
    this.rotation = (angle+360) % 360;
  }
  public void setMoveSpeed(double moveSpeed) {
    this.moveSpeed = moveSpeed;
  }
  public void setDamage(int damage) {
    this.damage = damage;
  }

  @Override
  public void setSprite(String fileName) {
    BufferedImage _spriteImg = GameWorld.loadSprite(fileName);
    if (_spriteImg != null) {
      multiSprite.setSpriteStrip(_spriteImg);
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    BufferedImage _sprite = multiSprite.getSubSpriteByRotation(rotation);
    graphics.drawImage(_sprite, (int)position.x, (int)position.y, null);
  }
}
