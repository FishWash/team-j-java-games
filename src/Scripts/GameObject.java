package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject
{
  protected Point position;
  protected BufferedImage sprite;

  public GameObject(Point position) {
    this.position = position;
  }

  public Point getPosition() {
    return position;
  }

  public void drawSprite(Graphics graphics) {
    graphics.drawImage(sprite, position.x, position.y, null);
  }
}
