package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tank extends GameObject implements Updatable, Damageable
{
  private int health = 100;
  private double moveSpeed = 2;
  private Vector2 moveVector = new Vector2(0, 0);
  private double rotation = 0; //rotation in angles. (0-360)
  private double turnSpeed = 2;

  private MultiSprite multiSprite;

  private TankInput tankInput;

  public Tank(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
    multiSprite = new MultiSprite(GameWorld.loadSprite("Tank_blue_base_strip60.png"), 60);
  }

  public void update() {
    rotation = (rotation+360 - tankInput.getTurnInput()*turnSpeed) % 360;
    moveVector.x = tankInput.getMoveInput()*moveSpeed * Math.cos(Math.toRadians(rotation));
    moveVector.y = -tankInput.getMoveInput()*moveSpeed * Math.sin(Math.toRadians(rotation));

    position.addVector(moveVector);
  }

  public Vector2 getTankCenterPosition(){
    Vector2 centerPosition = new Vector2(position.x + (sprite.getWidth() / 2), position.y + (sprite.getWidth() / 2));
    return centerPosition;
  }

  public void takeDamage(int damage) {
    health -= damage;
    if (health <= 0)
      die();
  }

  public void setTankInput(TankInput tankInput) {
    this.tankInput = tankInput;
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

  private void die() {
    // do die stuff
  }
}
