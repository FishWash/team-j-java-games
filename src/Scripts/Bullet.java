package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject implements ClockObserver
{
  private final double MOVE_SPEED = 5;

  private Vector2 moveVector = new Vector2(0, 0);
  private double rotation = 0; //rotation in angles. (0-360)
  private BoxCollider collider;
  private MultiSprite multiSprite;

  public Bullet(Vector2 position) {
    super(position);
    this.sprite = GameWorld.loadSprite("Shell_basic.png");
    this.multiSprite = multiSprite = new MultiSprite(GameWorld.loadSprite("Shell_heavy_strip60.png"), 60);
    this.collider = new BoxCollider(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    this.moveVector = Vector2.newMagnitudeVector(MOVE_SPEED);

    this.position = Vector2.subtractVectors( position, new Vector2(sprite.getWidth()/2, sprite.getHeight()/2 ));
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
    BufferedImage _sprite = multiSprite.getSubSpriteByRotation(rotation);
    graphics.drawImage(_sprite, (int)position.x, (int)position.y, null);
  }

  public void update() {
    move();
  }

  private void move() {
    position = Vector2.addVectors( position, moveVector);
    GameWorld.checkCollisions(collider);
  }

  public void setRotation(double angle) {
    this.rotation = (angle+360) % 360;
    moveVector = Vector2.newRotationMagnitudeVector(rotation, MOVE_SPEED);
  }
}
