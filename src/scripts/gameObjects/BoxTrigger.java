package scripts.gameObjects;

import scripts.utility.Vector2;

public abstract class BoxTrigger extends Trigger
{
  private GameObject parentGameObject;
  private Vector2 size;

  public BoxTrigger(GameObject parentGameObject, Vector2 size) {
    super(parentGameObject);
    this.size = size;
  }

  public BoxTrigger(GameObject parentGameObject) {
    super(parentGameObject);
    this.size = new Vector2(0, 0);
  }

  public boolean isOverlapping(Trigger otherTrigger) {

    if (this.equals(otherTrigger)) {
      return false;
    }

    if (otherTrigger instanceof BoxTrigger) {
      BoxTrigger otherBoxTrigger = (BoxTrigger) otherTrigger;
      return (checkOverlapping(this.getMinCorner(), this.getMaxCorner(),
                               otherBoxTrigger.getMinCorner(), otherBoxTrigger.getMaxCorner()));
    }

    return false;
  }

  public Vector2 getSize() {
    return size;
  }
  // Returns in-world position of top-left corner.
  public abstract Vector2 getMinCorner();
  // Returns in-world position of bottom-right corner.
  public abstract Vector2 getMaxCorner();

  public void setSize(Vector2 size) {
    this.size = size;
  }

  public static boolean checkOverlapping(Vector2 firstMinCorner, Vector2 firstMaxCorner,
                                         Vector2 secondMinCorner, Vector2 secondMaxCorner)
  {
    boolean xColliding=false, yColliding=false;

    if (secondMinCorner.x >= firstMinCorner.x) {
      if (firstMaxCorner.x >= secondMinCorner.x) {
        xColliding = true;
      }
    }
    else {
      if (secondMaxCorner.x >= firstMinCorner.x) {
        xColliding = true;
      }
    }

    if (secondMinCorner.y >= firstMinCorner.y) {
      if (firstMaxCorner.y >= secondMinCorner.y) {
        yColliding = true;
      }
    }
    else {
      if (secondMaxCorner.y >= firstMinCorner.y) {
        yColliding = true;
      }
    }

    return (xColliding && yColliding);
  }
}
