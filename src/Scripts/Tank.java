package Scripts;

import java.awt.*;

public class Tank extends GameObject implements Damageable
{
  private int health;
  private TankInput tankInput;

  public Tank(Point position) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
    health = 100;
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
