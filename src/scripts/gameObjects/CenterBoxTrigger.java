package scripts.gameObjects;

import scripts.utility.Vector2;

// CenterBoxTrigger is a BoxTrigger with its position in the center of the BoxTrigger.
public class CenterBoxTrigger extends BoxTrigger {

  public CenterBoxTrigger(GameObject parentGameObject, Vector2 size) {
    super(parentGameObject, size);
  }

  @Override
  public Vector2 getMinCorner() {
    return Vector2.subtractVectors(this.getPosition(), Vector2.multiplyVector(getSize(), 0.5));
  }
  @Override
  public Vector2 getMaxCorner() {
    return Vector2.addVectors(this.getPosition(), Vector2.multiplyVector(getSize(), 0.5));
  }
}
