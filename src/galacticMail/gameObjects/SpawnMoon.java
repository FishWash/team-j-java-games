package galacticMail.gameObjects;

import utility.SpriteHandler;
import utility.Vector2;

public class SpawnMoon extends Moon {
  public SpawnMoon(Vector2 position) {
    super(position, 0);
    moveSpeed = 0;
    sprite = SpriteHandler.getInstance().loadSprite("Moon.png");
  }
}
