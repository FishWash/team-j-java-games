package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
  protected boolean alive = true;
  protected Vector2 position = new Vector2(0, 0);
  protected double rotation = 0;
  protected BufferedImage sprite;
  protected GameWorld.Player owner = GameWorld.Player.Neutral;
  protected GameWorld.RenderingLayer renderingLayer = GameWorld.RenderingLayer.BackgroundGameObject;

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
  public boolean getAlive() {
    return alive;
  }
  public GameWorld.Player getOwner() {
    return owner;
  }
  public GameWorld.RenderingLayer getRenderingLayer() {
    return renderingLayer;
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
    BufferedImage _spriteImg = GameWorld.getInstance().loadSprite(fileName);
    if (_spriteImg != null) {
      sprite = _spriteImg;
    }
  }

  // Other
  protected void die() {
    GameWorld.destroy(this);
    alive = false;
  }
}
