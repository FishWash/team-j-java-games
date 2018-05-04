package scripts.gameObjects.explosions;

import scripts.gameWorlds.TankGameWorld;
import scripts.gameObjects.CenterBoxTrigger;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

public class ShellExplosion extends Explosion {
  public ShellExplosion (Vector2 position, TankGameWorld.Player owner) {
    super(position, owner);

    damage = 24;
    multiSprite = new MultiSprite(TankGameWorld.getInstance().loadSprite(
                          "Explosion_small_strip6.png"), 6);
    sprite = multiSprite.getSubSprite(0);
    if (sprite != null) {
      trigger = new CenterBoxTrigger(this,
                           new Vector2(sprite.getWidth(), sprite.getHeight() ));
    }
    TankGameWorld.getInstance().playSound("smallexplosion.wav");
  }
}
