package Scripts;

public class DestructibleWall extends GameObject implements Collidable, Damageable
{
  public DestructibleWall(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_destructible.png");
  }

  public void takeDamage(int damageAmount) {
    die();
  }

  private void die()
  {
    // do die stuff
  }
}