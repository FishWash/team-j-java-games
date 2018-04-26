package scripts.gameObjects;

import scripts.utility.Vector2;

public class BoxTrigger
{
  private GameObject parent;
  private Vector2 size;

  public BoxTrigger(GameObject parent, Vector2 size) {
    this.parent = parent;
    this.size = size;
  }

  public BoxTrigger(GameObject parent) {
    this.parent = parent;
    this.size = new Vector2(0, 0);
  }

  public boolean isOverlapping(BoxTrigger otherTrigger) {

    if (this.equals(otherTrigger)) {
      return false;
    }

    return (checkOverlapping(this.getPosition(), this.getCorner(),
                             otherTrigger.getPosition(), otherTrigger.getCorner()));
  }

  public static boolean checkOverlapping(Vector2 firstTriggerPosition, Vector2 firstTriggerCorner,
                                         Vector2 secondTriggerPosition, Vector2 secondTriggerCorner)
  {
    boolean xColliding=false, yColliding=false;

    if (secondTriggerPosition.x >= firstTriggerPosition.x) {
      if (firstTriggerCorner.x >= secondTriggerPosition.x) {
        xColliding = true;
      }
    }
    else {
      if (secondTriggerCorner.x >= firstTriggerPosition.x) {
        xColliding = true;
      }
    }

    if (secondTriggerPosition.y >= firstTriggerPosition.y) {
      if (firstTriggerCorner.y >= secondTriggerPosition.y) {
        yColliding = true;
      }
    }
    else {
      if (secondTriggerCorner.y >= firstTriggerPosition.y) {
        yColliding = true;
      }
    }

    return (xColliding && yColliding);
  }

  // Returns in-world position of top-left corner.
  public Vector2 getPosition() {
    return parent.position;
  }
  public Vector2 getSize() {
    return size;
  }
  // Returns in-world position of bottom-right corner.
  public Vector2 getCorner() {
    return Vector2.addVectors(getPosition(), size);
  }

  public void setSize(Vector2 size) {
    this.size = size;
  }
}
