package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

public class ShellExplosion extends Explosion {
  public ShellExplosion (Vector2 position, GameWorld.Player owner) {
    super(position, owner);

//    damage = 12;
    damage = 40;
    multiSprite = new MultiSprite(GameWorld.getInstance().loadSprite("Explosion_small_strip6.png"), 6);
    sprite = multiSprite.getSubSprite(0);
    if (sprite != null) {
      boxTrigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight() ));
    }
  }
}
