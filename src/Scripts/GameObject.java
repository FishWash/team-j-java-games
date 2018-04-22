package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
  protected Vector2 position;
  protected BufferedImage sprite;

  public GameObject() {
    this.position = Vector2.ZERO;
  }
  public GameObject(Vector2 position) {
    this.position = position;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void drawSprite(Graphics graphics)
  {
    if (sprite != null) {
      graphics.drawImage(sprite, (int)position.x, (int)position.y, null);
    }
  }

  public void setSprite(String fileName)
  {
    BufferedImage _spriteImg = GameWorld.loadSprite(fileName);
    if (_spriteImg != null) {
      sprite = _spriteImg;
    }
  }

  public BufferedImage getSprite() {
    return sprite;
  }

  public Vector2 getCenterPosition(){
    Vector2 centerPosition = new Vector2(position.x + (sprite.getWidth() / 2), position.y + (sprite.getWidth() / 2));
    return centerPosition;
  }
}
