package general.gameObjects;

import utility.Vector2;

// CornerBoxTrigger is a BoxTrigger with its position at the top-left corner.
public class CornerBoxTrigger extends BoxTrigger {

  public CornerBoxTrigger(GameObject parentGameObject, Vector2 size) {
    super(parentGameObject, size);
  }
  public CornerBoxTrigger(GameObject parentGameObject) {
    super(parentGameObject);
  }

  @Override
  public Vector2 getMinCorner() {
    return this.getPosition();
  }
  @Override
  public Vector2 getMaxCorner() {
    return Vector2.addVectors(getPosition(), getSize());
  }
}
