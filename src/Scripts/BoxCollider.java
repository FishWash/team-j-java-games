package Scripts;

public class BoxCollider
{
  private GameObject parent;
  private Vector2 size;

  public BoxCollider(GameObject parent, Vector2 size) {
    this.parent = parent;
    this.size = size;
  }

  public BoxCollider(GameObject parent) {
    this.parent = parent;
    this.size = Vector2.zero;
  }

  public boolean isCollidingWith(BoxCollider otherCollider) {
    boolean xColliding=false, yColliding=false;

    if (this.equals(otherCollider)) {
      return false;
    }

    //System.out.println(position + " " + getCorner() + ", " + otherCollider.position + " " + otherCollider.getCorner());

    if (otherCollider.getPosition().x >= getPosition().x) {
      if (getCorner().x >= otherCollider.getPosition().x) {
        xColliding = true;
      }
    }
    else {
      if (otherCollider.getCorner().x >= getPosition().x) {
        xColliding = true;
      }
    }

    if (otherCollider.getPosition().y >= getPosition().y) {
      if (getCorner().y >= otherCollider.getPosition().y) {
        yColliding = true;
      }
    }
    else {
      if (otherCollider.getCorner().y >= getPosition().y) {
        yColliding = true;
      }
    }

    return (xColliding && yColliding);
  }

  public Vector2 getPosition() {
    return parent.position;
  }

  // returns position of bottom right corner
  public Vector2 getCorner() {
    return parent.position.addVector(size);
  }

  public void setSize(Vector2 size) {
    this.size = size;
  }
}
