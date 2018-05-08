package general.gameObjects;

import general.GameWorld;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
  protected boolean alive = true;
  protected Vector2 position = new Vector2(0, 0);
  protected double rotation = 0;
  protected BufferedImage sprite;

  public GameObject() {
  }
  public GameObject(Vector2 position) {
    this.position = position;
  }

  // Public Methods
  public void drawSprite(Graphics graphics) {
    if (sprite != null) {
      double xPos = position.x - sprite.getWidth()/2;
      double yPos = position.y - sprite.getHeight()/2;
      graphics.drawImage(sprite, (int)xPos, (int)yPos, null);
    }
  }

  // Getters
  public boolean isAlive() {
    return alive;
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
  public void setPosition(Vector2 position) {
    this.position = position;
  }
  public void setRotation(double rotation) {
    this.rotation = (rotation+360) % 360;
  }
  public void setSprite(String fileName) {
    BufferedImage spriteImage = GameWorld.getInstance().loadSprite(fileName);
    if (spriteImage != null) {
      sprite = spriteImage;
    }
  }

  // Other
  protected void die() {
    if (alive) {
      GameWorld.getInstance().destroy(this);
      alive = false;
    }
  }
}
