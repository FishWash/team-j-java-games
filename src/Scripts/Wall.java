package Scripts;

import java.awt.*;

public class Wall extends GameObject implements Collidable
{
  public Wall(Vector2D position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_indestructible.png");
  }
}
