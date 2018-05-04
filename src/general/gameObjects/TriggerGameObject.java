package general.gameObjects;

import tankGame.gameObjects.TankGameObject;
import utility.Vector2;

public abstract class TriggerGameObject extends TankGameObject
{
  protected Trigger trigger;

  public TriggerGameObject(Vector2 position) {
    super(position);
  }

  public Trigger getTrigger() {
    return trigger;
  }

  public boolean isOverlappingBox(BoxTrigger otherBoxTrigger) {
    if (trigger instanceof BoxTrigger) {
      BoxTrigger boxTrigger = (BoxTrigger) trigger;
      return BoxTrigger.checkOverlapping(boxTrigger.getMinCorner(), boxTrigger.getMaxCorner(),
                                         otherBoxTrigger.getMinCorner(), otherBoxTrigger.getMaxCorner());
    }
    return false;
  }
}
