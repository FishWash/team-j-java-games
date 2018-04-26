package scripts.gameObjects;

import scripts.gameObjects.BoxTrigger;
import scripts.gameObjects.GameObject;
import scripts.utility.Vector2;

public abstract class TriggerGameObject extends GameObject
{
  protected BoxTrigger trigger;

  public TriggerGameObject() {
    super();
    trigger = new BoxTrigger(this);
  }
  public TriggerGameObject(Vector2 position) {
    super(position);
    trigger = new BoxTrigger(this);
  }

  public BoxTrigger getTrigger() {
    return trigger;
  }

  public boolean isOverlapping(BoxTrigger otherTrigger) {
    return trigger.isOverlapping(otherTrigger);
  }
}
