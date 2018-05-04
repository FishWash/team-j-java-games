package scripts.gameObjects;

import scripts.utility.Vector2;

public class CircleTrigger extends Trigger {

  private double radius;

  public CircleTrigger(GameObject parentGameObject, double radius) {
    super(parentGameObject);
    this.radius = radius;
  }

  public boolean isOverlapping(Trigger otherTrigger) {
    if (this.equals(otherTrigger)) {
      return false;
    }

    if (otherTrigger instanceof CircleTrigger) {
      CircleTrigger otherCircleTrigger = (CircleTrigger) otherTrigger;
      Vector2 differenceVector = Vector2.subtractVectors( getPosition(), otherCircleTrigger.getPosition() );
      return (differenceVector.getMagnitude() <= radius + otherCircleTrigger.radius);
    }

    return false;
  }
}
