package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
  protected Vector2D position;
  protected BufferedImage sprite;

  public GameObject(Vector2D position) {
    this.position = position;
  }

  public Vector2D getPosition() {
    return position;
  }

  public void drawSprite(Graphics graphics) {
    graphics.drawImage(sprite, (int)position.x, (int)position.y, null);
  }
}
