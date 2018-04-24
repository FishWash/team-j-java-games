package Scripts;

public class DestructibleWall extends TriggerGameObject implements Damageable
{
  public DestructibleWall(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_destructible.png");
    trigger.setSize(new Vector2(sprite.getWidth(), sprite.getHeight()));
  }
  public void takeDamage(int damageAmount) {
    die();
  }
}