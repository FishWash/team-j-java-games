package scripts.gameObjects;

import scripts.utility.Vector2;

public abstract class Trigger {

  protected GameObject parentGameObject;

  public Trigger(GameObject parentGameObject) {
    this.parentGameObject = parentGameObject;
  }

  public Vector2 getPosition() {
    return parentGameObject.getPosition();
  }
  public abstract boolean isOverlapping(Trigger otherTrigger);
}
