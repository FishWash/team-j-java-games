package Scripts;

public class CollidableGameObject extends TriggerGameObject implements Collidable
{
  public CollidableGameObject(Vector2 position) {
    super(position);
  }

  public Vector2 getMoveVectorWithCollision(BoxTrigger otherTrigger, Vector2 moveVector) {
    if (trigger.equals(otherTrigger)) {
      return moveVector;
    }

    Vector2 nextPosition = Vector2.addVectors(otherTrigger.getPosition(), moveVector);
    Vector2 nextCorner = Vector2.addVectors(otherTrigger.getCorner(), moveVector);

    if (BoxTrigger.checkOverlapping(trigger.getPosition(), trigger.getCorner(), nextPosition, nextCorner)) {

//      double xOverlap=0, yOverlap=0;
//      if (moveVector.x > 0) {
//        xOverlap = nextCorner.x - trigger.getPosition().x;
//      }
//      else if (moveVector.x < 0) {
//        xOverlap = nextPosition.x - trigger.getCorner().x;
//      }
//      if (moveVector.y > 0) {
//        yOverlap = nextCorner.y - trigger.getPosition().y;
//      }
//      else if (moveVector.y < 0) {
//        yOverlap = nextPosition.y - trigger.getCorner().y;
//      }
//
//      if (Math.abs(moveVector.x) >= Math.abs(moveVector.y)) {
//        moveVector.x -= xOverlap;
//      }
//      else {
//        moveVector.y -= yOverlap;
//      }

//      Vector2 differenceVector;
//      if (moveVector.x >= 0) {
//        if (moveVector.y >= 0) {
//          differenceVector = Vector2.subtractVectors(nextCorner, trigger.getPosition());
//        }
//        else {
//          differenceVector = new Vector2(nextCorner.x - trigger.getPosition().x,
//                                         nextPosition.y - trigger.getCorner().y);
//        }
//      }
//      else {
//        if (moveVector.y >= 0) {
//          differenceVector = new Vector2(nextPosition.x - trigger.getCorner().x,
//                                         nextCorner.y - trigger.getPosition().y);
//        }
//        else {
//          differenceVector = Vector2.subtractVectors(nextPosition, trigger.getCorner());
//        }
//      }
//
//      if (Math.abs(differenceVector.x) < Math.abs(differenceVector.y)) {
//        return new Vector2(differenceVector.x, 0);
//      }
//      else {
//        return new Vector2(0, differenceVector.y);
//      }

//      if (otherTrigger.getPosition().x >= trigger.getCorner().x) {
//
//      }
//      else if (otherTrigger.getCorner().x <= trigger.getPosition().x) {
//
//      }

      if (otherTrigger.getPosition().x >= trigger.getCorner().x) {
        moveVector.x = trigger.getCorner().x - otherTrigger.getPosition().x;
      }
      else if (otherTrigger.getCorner().x <= trigger.getPosition().x) {
        moveVector.x = trigger.getPosition().x - otherTrigger.getCorner().x;
      }
      else if (otherTrigger.getPosition().y >= trigger.getCorner().y) {
        moveVector.y = trigger.getCorner().y - otherTrigger.getPosition().y;
      }
      else if (otherTrigger.getCorner().y <= trigger.getPosition().y) {
        moveVector.y = trigger.getPosition().y - otherTrigger.getCorner().y;
      }
    }

    return moveVector;
  }

}
