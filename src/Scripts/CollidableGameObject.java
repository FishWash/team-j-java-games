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

  public void setTriggerSize(Vector2 triggerSize) {
    trigger.setSize(triggerSize);
  }
}
