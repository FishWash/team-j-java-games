package scripts.gameObjects.explosions;

import scripts.gameObjects.CenterBoxTrigger;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.AnimatedSprite;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

public class NukeExplosion extends Explosion {
  public NukeExplosion (Vector2 position) {
    super(position, TankGameWorld.Player.Neutral);

    damage = 100;
    MultiSprite multiSprite = new MultiSprite(TankGameWorld.getInstance()
          .loadSprite("nuke_explosion_strip32.png"), 32);
    animatedSprite = new AnimatedSprite(multiSprite, 8);
    sprite = multiSprite.getSubSprite(0);

    if (sprite != null) {
      trigger = new CenterBoxTrigger(this,
                           new Vector2(sprite.getWidth(), sprite.getHeight()));
    }
    TankGameWorld.getInstance().playSound("nuke_explosion.wav");
  }
}
