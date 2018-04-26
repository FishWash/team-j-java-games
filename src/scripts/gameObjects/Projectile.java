package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends TriggerGameObject implements ClockListener
{
  protected double moveSpeed = 1;
  protected MultiSprite multiSprite;
  protected Timer lifeTimer;

  public Projectile(int lifeTime, GameWorld.Player owner) {
    super();
    sprite = GameWorld.loadSprite("Shell_basic.png");
    multiSprite = new MultiSprite(GameWorld.loadSprite("Shell_basic_strip60.png"), 60);
    trigger = new BoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    lifeTimer = new Timer(lifeTime);
    this.owner = owner;
    renderingLayer = GameWorld.RenderingLayer.Projectiles;
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
    Collidable c = GameWorld.findOverlappingCollidable(trigger);
    if (c != null && (owner == GameWorld.Player.Neutral || ((GameObject) c).getOwner() != owner)) {
      die();
    }
  }

  @Override
  public void die() {
    super.die();
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
