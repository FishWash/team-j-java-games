package scripts.gameObjects;

import scripts.Collidable;
import scripts.utility.Vector2;

// CollidableGameObject has no sprite. Only for collision.
public abstract class BoxCollidableGameObject extends BoxTriggerGameObject implements Collidable
{

  public BoxCollidableGameObject(Vector2 position) {
    super(position);
    boxTrigger = new CornerBoxTrigger(this);
  }

  public Vector2 getMoveVectorWithCollision(BoxTrigger otherBoxTrigger, Vector2 moveVector) {
    if (boxTrigger.equals(otherBoxTrigger)) {
      return moveVector;
    }

    Vector2 nextMinCorner = Vector2.addVectors(otherBoxTrigger.getMinCorner(), moveVector);
    Vector2 nextMaxCorner = Vector2.addVectors(otherBoxTrigger.getMaxCorner(), moveVector);

    if (BoxTrigger.checkOverlapping(boxTrigger.getMinCorner(), boxTrigger.getMaxCorner(), nextMinCorner, nextMaxCorner)) {
      if (otherBoxTrigger.getMinCorner().x >= boxTrigger.getMaxCorner().x) {
        moveVector.x = boxTrigger.getMaxCorner().x - otherBoxTrigger.getMinCorner().x;
      }
      else if (otherBoxTrigger.getMaxCorner().x <= boxTrigger.getMinCorner().x) {
        moveVector.x = boxTrigger.getMinCorner().x - otherBoxTrigger.getMaxCorner().x;
      }
      else if (otherBoxTrigger.getMinCorner().y >= boxTrigger.getMaxCorner().y) {
        moveVector.y = boxTrigger.getMaxCorner().y - otherBoxTrigger.getMinCorner().y;
      }
      else if (otherBoxTrigger.getMaxCorner().y <= boxTrigger.getMinCorner().y) {
        moveVector.y = boxTrigger.getMinCorner().y - otherBoxTrigger.getMaxCorner().y;
      }
    }

    return moveVector;
  }
}
