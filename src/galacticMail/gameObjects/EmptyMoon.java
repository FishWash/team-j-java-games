package galacticMail.gameObjects;

import general.GameWorld;
import utility.Vector2;

public class EmptyMoon extends Moon {
  public EmptyMoon(Vector2 position) {
    super(position, 0);
    moveSpeed = 0;
    sprite = GameWorld.getInstance().loadSprite("Moon.png");
  }
}
