package scripts.gameObjects.spaceObjects;

import scripts.utility.Vector2;

public class Moon extends SpaceObject {
  public Moon(Vector2 position, double rotation) {
    super(position, 32);
    moveSpeed = 5;
    renderingLayerIndex = 1;
  }
}
