package tankGame.gameObjects;

import general.gameObjects.BoxCollidableGameObject;
import general.gameObjects.CornerBoxTrigger;
import utility.Vector2;

public class WallCollidable extends BoxCollidableGameObject {
  public WallCollidable(Vector2 position) {
    super(position);
    trigger = new CornerBoxTrigger(this);
  }

  public void setSize(Vector2 newSize) {
    CornerBoxTrigger cornerBoxTrigger = (CornerBoxTrigger) trigger;
    cornerBoxTrigger.setSize(newSize);
  }

  public void addSize(Vector2 addSize) {
    CornerBoxTrigger cornerBoxTrigger = (CornerBoxTrigger) trigger;
    cornerBoxTrigger.setSize(
            Vector2.addVectors(cornerBoxTrigger.getSize(), addSize) );
  }
}
