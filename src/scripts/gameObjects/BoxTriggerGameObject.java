package scripts.gameObjects;

import scripts.gameObjects.BoxTrigger;
import scripts.gameObjects.GameObject;
import scripts.utility.Vector2;

public abstract class BoxTriggerGameObject extends GameObject
{
  protected BoxTrigger boxTrigger;

  public BoxTriggerGameObject(Vector2 position) {
    super(position);
  }

  public Trigger getBoxTrigger() {
    return boxTrigger;
  }
  public void setBoxTriggerSize(Vector2 triggerSize) {
    boxTrigger.setSize(triggerSize);
  }
  public void addBoxTriggerSize(Vector2 triggerSize) {
    boxTrigger.setSize( Vector2.addVectors(boxTrigger.getSize(), triggerSize) );
  }

  public boolean isOverlapping(BoxTrigger otherTrigger) {
    return BoxTrigger.checkOverlapping(boxTrigger.getMinCorner(), boxTrigger.getMaxCorner(),
                                       otherTrigger.getMinCorner(), otherTrigger.getMaxCorner());
  }
}
