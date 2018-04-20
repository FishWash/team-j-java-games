package Scripts;

import java.awt.*;

public class DestructibleWall extends GameObject implements Collidable, Damageable
{
  public DestructibleWall(Point position) {
    super(position);
    sprite = GameWorld.loadSprite("wall.png");
  }
  public void takeDamage(int damageAmount) {
    die();
  }

  private void die()
  {
    // do die stuff
  }
}