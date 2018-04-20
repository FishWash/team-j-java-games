package Scripts;

public class DestructibleWall extends CollidableGameObject implements Damageable
{
  public DestructibleWall(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_destructible.png");
    collider.setSize(new Vector2(sprite.getWidth(), sprite.getHeight()));
  }
  public void takeDamage(int damageAmount) {
    die();
  }

  private void die()
  {
    // do die stuff
  }


}