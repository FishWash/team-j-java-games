package Scripts;

import java.awt.*;

public class DestructibleWall extends GameObject implements Collidable, Damageable
{
  public DestructibleWall(Vector2D position) {
    super(position);
  }

  public void takeDamage(int damageAmount) {
    die();
  }

  private void die()
  {
    // do die stuff
  }
}