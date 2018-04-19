package Scripts;

import java.awt.*;

public class Tank extends GameObject implements Updatable, Damageable
{
  int health = 100;
  float moveSpeed = 1;

  public Tank(Point position) {
    super(position);
    sprite = GameWorld.loadSprite("Tank_grey_basic.png");
  }

  public void update() {

  }

  public void takeDamage(int damage) {
    health -= damage;
    if (health <= 0)
      die();
  }

  private void die() {
    // do die stuff
  }
}
