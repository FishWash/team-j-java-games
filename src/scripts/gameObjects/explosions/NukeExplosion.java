package scripts.gameObjects.explosions;

import scripts.gameObjects.CenterBoxTrigger;
import scripts.gameWorlds.GameWorld;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

public class NukeExplosion extends Explosion {
  public NukeExplosion (Vector2 position) {
    super(position, GameWorld.Player.Neutral);

    damage = 100;
    animationFrameLength = 8;
    multiSprite = new MultiSprite(GameWorld.getInstance().loadSprite("nuke_explosion_strip32.png"), 32);
    sprite = multiSprite.getSubSprite(0);
    if (sprite != null) {
      boxTrigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    }
    GameWorld.getInstance().playSound("nuke_explosion.wav");
  }
}
