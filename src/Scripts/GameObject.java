package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
  protected boolean alive = true;
  protected GameWorld.Player owner;
  protected Vector2 position;
  protected double rotation;
  protected BufferedImage sprite;

  public GameObject() {
    this.position = new Vector2(0,0);
    this.rotation = 0;
  }
  public GameObject(Vector2 position) {
    this.position = position;
    this.rotation = 0;
  }

  public Vector2 getCenterPosition() {
    if (sprite != null) {
      return new Vector2(position.x + (sprite.getWidth()/2), position.y + (sprite.getWidth()/2));
    }
    return null;
  }
  public void drawSprite(Graphics graphics) {
    if (sprite != null) {
      graphics.drawImage(sprite, (int)position.x, (int)position.y, null);
    }
  }

  protected void die() {
    GameWorld.destroy(this);
    alive = false;
  }

  // Getters
  public boolean getAlive() {
    return alive;
  }
  public GameWorld.Player getOwner() {
    return owner;
  }
  public Vector2 getPosition() {
    return position;
  }
  public double getRotation() {
    return rotation;
  }
  public BufferedImage getSprite() {
    return sprite;
  }

  // Setters
  public void setAlive(boolean alive) {
    this.alive = alive;
  }
  public void setOwner(GameWorld.Player owner) {
    this.owner = owner;
  }
  public void setPosition(Vector2 position) {
    this.position = position;
  }
  public void movePosition(Vector2 moveVector) {
    this.position = Vector2.addVectors(position, moveVector);
  }
  public void setRotation(double rotation) {
    this.rotation = (rotation+360) % 360;
  }
  public void setSprite(String fileName) {
    BufferedImage _spriteImg = GameWorld.loadSprite(fileName);
    if (_spriteImg != null) {
      sprite = _spriteImg;
    }
  }
}
