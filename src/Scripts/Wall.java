package Scripts;

public class Wall extends CollidableGameObject
{
  public Wall(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_indestructible2.png");
    trigger.setSize(new Vector2(sprite.getWidth(), sprite.getHeight()));
  }
}
