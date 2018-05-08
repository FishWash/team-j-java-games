package galacticMail.gameObjects;

import general.GameWorld;
import utility.Vector2;

public class SpawnMoon extends Moon {
  public SpawnMoon(Vector2 position) {
    super(position, 0);
    moveSpeed = 0;
    sprite = GameWorld.getInstance().loadSprite("Moon.png");
  }
}
