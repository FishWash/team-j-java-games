package scripts.gameObjects;

import scripts.utility.Vector2;

public class WallCollidable extends BoxCollidableGameObject {
  public WallCollidable(Vector2 position) {
    super(position);
    boxTrigger = new CornerBoxTrigger(this);
  }
}
