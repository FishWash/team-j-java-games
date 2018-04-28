package scripts.gameObjects.pickups;

import scripts.gameObjects.BoxTriggerGameObject;
import scripts.gameObjects.CenterBoxTrigger;
import scripts.gameObjects.Tank;
import scripts.gameWorlds.GameWorld;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

public abstract class Pickup extends BoxTriggerGameObject {
  public Pickup(Vector2 position, int ballStripIndex) {
    super(position);
    MultiSprite multiSprite = new MultiSprite(GameWorld.getInstance().loadSprite("Ball_strip9.png"), 9);
    sprite = multiSprite.getSubSprite(ballStripIndex);
    boxTrigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
  }
  public abstract void activatePickup(Tank tank);
}