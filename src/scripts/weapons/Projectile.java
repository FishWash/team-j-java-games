package scripts.weapons;

import scripts.GameWorld;
import scripts.gameObjects.BoxTriggerGameObject;
import scripts.gameObjects.CenterBoxTrigger;
import scripts.Collidable;
import scripts.gameObjects.GameObject;
import scripts.utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends BoxTriggerGameObject implements ClockListener
{
  protected double moveSpeed = 1;
  protected MultiSprite multiSprite;
  protected Timer lifeTimer;

  public Projectile(Vector2 position, int lifeTime, GameWorld.Player owner) {
    super(position);
    sprite = GameWorld.getInstance().loadSprite("Shell_basic.png");
    multiSprite = new MultiSprite(GameWorld.getInstance().loadSprite("Shell_basic_strip60.png"), 60);
    boxTrigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
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
    Collidable c = GameWorld.findOverlappingCollidable(boxTrigger);
    if (c != null && (owner == GameWorld.Player.Neutral || ((GameObject) c).getOwner() != owner)) {
      die();
    }
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
