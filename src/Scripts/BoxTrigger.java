package Scripts;

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
    this.size = Vector2.ZERO;
  }

  public boolean isOverlapping(BoxTrigger otherTrigger) {

    boolean xColliding=false, yColliding=false;

    if (this.equals(otherTrigger)) {
      return false;
    }

    if (otherTrigger.getPosition().x >= this.getPosition().x) {
      if (this.getCorner().x >= otherTrigger.getPosition().x) {
        xColliding = true;
      }
    }
    else {
      if (otherTrigger.getCorner().x >= this.getPosition().x) {
        xColliding = true;
      }
    }

    if (otherTrigger.getPosition().y >= this.getPosition().y) {
      if (this.getCorner().y >= otherTrigger.getPosition().y) {
        yColliding = true;
      }
    }
    else {
      if (otherTrigger.getCorner().y >= this.getPosition().y) {
        yColliding = true;
      }
    }

    return (xColliding && yColliding);
  }

  public Vector2 getPosition() {
    return parent.position;
  }

  // Returns in-world position of bottom-right corner.
  public Vector2 getCorner() {
    return Vector2.addVectors(parent.position, this.size);
  }

  public void setSize(Vector2 size) {
    this.size = size;
  }
}
