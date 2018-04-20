package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements Updatable
{
  private double moveSpeed = 5;
  private Vector2 moveVector = new Vector2(0, 0);
  private double rotation = 0; //rotation in angles. (0-360)
  private BoxCollider collider;

  private MultiSprite multiSprite;

  public Bullet(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("Shell_basic.png");
    multiSprite = multiSprite = new MultiSprite(GameWorld.loadSprite("Shell_basic_strip60.png"), 60);
    collider = new BoxCollider(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
  }

  @Override
  public void setSprite(String fileName)
  {
    BufferedImage _spriteImg = GameWorld.loadSprite(fileName);
    if (_spriteImg != null) {
      multiSprite.setSpriteStrip(_spriteImg);
    }
  }

  @Override
  public void drawSprite(Graphics graphics)
  {
    int index = (int) (rotation / (360 / multiSprite.getNumSubsprites()));
    BufferedImage _sprite = multiSprite.getSubsprite(index);
    graphics.drawImage(_sprite, (int)position.x, (int)position.y, null);
  }

  public void update() {
    move();
  }

  private void move() {
    moveVector.x = moveSpeed * Math.cos(Math.toRadians(rotation));
    moveVector.y = -moveSpeed * Math.sin(Math.toRadians(rotation));

    position = position.addVector(moveVector);

    GameWorld.checkCollisions(collider);
  }

  public void setRotation(double angle) {
    this.rotation = (angle+360) % 360;
  }
}
