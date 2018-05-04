package scripts.gameObjects.pickups;

import scripts.gameObjects.TriggerGameObject;
import scripts.gameObjects.CenterBoxTrigger;
import scripts.gameObjects.Tank;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

public abstract class Pickup extends TriggerGameObject {
  public Pickup(Vector2 position, int ballStripIndex) {
    super(position);
    MultiSprite multiSprite = new MultiSprite(TankGameWorld.getInstance().loadSprite("Ball_strip9.png"), 9);
    sprite = multiSprite.getSubSprite(ballStripIndex);
    trigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
  }
  public abstract void activatePickup(Tank tank);
}
