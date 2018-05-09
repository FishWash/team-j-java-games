package tankGame.gameObjects.pickups;

import general.gameObjects.CenterBoxTrigger;
import general.gameObjects.TriggerGameObject;
import tankGame.TankGameWorld;
import tankGame.gameObjects.Tank;
import utility.MultiSprite;
import utility.SpriteHandler;
import utility.Vector2;

public abstract class Pickup extends TriggerGameObject {
  public Pickup(Vector2 position, int ballStripIndex) {
    super(position);
    MultiSprite multiSprite = new MultiSprite(SpriteHandler.getInstance().loadSprite("Ball_strip9.png"), 9);
    sprite = multiSprite.getSubSprite(ballStripIndex);
    trigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
  }
  public abstract void activatePickup(Tank tank);
}
