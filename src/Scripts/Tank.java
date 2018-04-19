package Scripts;

import java.awt.*;

public class Tank extends GameObject implements Updatable, Damageable
{
  private int health = 100;
  private float moveSpeed = 2;
  private Vector2D moveVector = new Vector2D(0,0);
  private float rotation = 0; //rotation in angles. (0-360)
  private float turnSpeed = 0.05f;

  private TankInput tankInput;

  public Tank(Vector2D position) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
  }

  public void update() {
    rotation = (rotation - tankInput.getTurnInput()*turnSpeed) % 360;
    moveVector.x = tankInput.getMoveInput()*moveSpeed * Math.cos(rotation);
    moveVector.y = tankInput.getMoveInput()*moveSpeed * Math.sin(rotation);

    position.addVector(moveVector);
  }

  public void takeDamage(int damage) {
    health -= damage;
    if (health <= 0)
      die();
  }

  public void setTankInput(TankInput tankInput) {
    this.tankInput = tankInput;
  }

  private void die() {
    // do die stuff
  }
}
