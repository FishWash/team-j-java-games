package tankGame.gameObjects.explosions;

import general.gameObjects.CenterBoxTrigger;
import tankGame.TankGameWorld;
import utility.*;

public class NukeExplosion extends Explosion {
  public NukeExplosion (Vector2 position) {
    super(position, TankGameWorld.Player.Neutral);

    damage = 100;
    MultiSprite multiSprite = new MultiSprite(SpriteHandler.getInstance()
            .loadSprite("nuke_explosion_strip32.png"), 32);
    animatedSprite = new AnimatedSprite(multiSprite, 8);
    sprite = multiSprite.getSubSprite(0);

    if (sprite != null) {
      trigger = new CenterBoxTrigger(this,
                                     new Vector2(sprite.getWidth(), sprite.getHeight()));
    }
    SoundHandler.getInstance().playSound("nuke_explosion.wav");
  }
}
