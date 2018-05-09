package tankGame.gameObjects.projectiles;

import general.GameWorld;
import general.gameObjects.*;
import tankGame.TankGameWorld;
import tankGame.gameObjects.TankGameObject;
import utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends TriggerGameObject implements ClockListener
{
  protected double moveSpeed = 1;
  protected MultiSprite multiSprite;
  protected Timer lifeTimer;
  protected int damage = 0;

  public Projectile(Vector2 position, int lifeTime, TankGameWorld.Player owner) {
    super(position);
    sprite = SpriteHandler.getInstance().loadSprite("Shell_basic.png");
    multiSprite = new MultiSprite(SpriteHandler.getInstance().loadSprite("Shell_basic_strip60.png"), 60);
    trigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    lifeTimer = new Timer(lifeTime);
    this.owner = owner;
    renderingLayer = TankGameWorld.RenderingLayer.Projectiles;
  }

  public void update() {
    checkCollidables();
    move();

    if (lifeTimer.isDone()) {
      die();
    }
  }

  private void move() {
    Vector2 moveVector = Vector2.newRotationMagnitudeVector(rotation, moveSpeed);
    position = Vector2.addVectors( position, moveVector );
  }

  protected void checkCollidables() {
    Collidable c = CollisionHandler.getInstance().findOverlappingCollidable((BoxTrigger)trigger);
    if (c != null) {
      if (c instanceof TankGameObject) {
        if (owner == TankGameWorld.Player.Neutral || ((TankGameObject) c).getOwner() != owner) {
          if (c instanceof Damageable) {
            ((Damageable) c).damage(damage);
          }
          die();
        }
      }
      else {
        die();
      }
    }
  }

  // Sprite stuff
  @Override
  public void setSprite(String fileName) {
    BufferedImage spriteStrip = SpriteHandler.getInstance().loadSprite(fileName);
    if (spriteStrip != null) {
      multiSprite.setSpriteStrip(spriteStrip);
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = multiSprite.getSubSpriteByRotation(rotation);
    super.drawSprite(graphics);
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
}
