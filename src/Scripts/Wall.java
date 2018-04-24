package Scripts;

public class Wall extends GameObject
{
  public Wall(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_indestructible2.png");
  }
}
