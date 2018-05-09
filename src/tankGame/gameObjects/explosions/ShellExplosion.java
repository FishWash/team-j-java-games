package tankGame.gameObjects.explosions;

import general.gameObjects.CenterBoxTrigger;
import tankGame.TankGameWorld;
import utility.*;

public class ShellExplosion extends Explosion {
  public ShellExplosion (Vector2 position, TankGameWorld.Player owner) {
    super(position, owner);

    damage = 24;
    MultiSprite multiSprite = new MultiSprite(SpriteHandler.getInstance()
            .loadSprite("Explosion_small_strip6.png"), 6);
    animatedSprite = new AnimatedSprite(multiSprite, 5);
    sprite = multiSprite.getSubSprite(0);

    if (sprite != null) {
      trigger = new CenterBoxTrigger(this,
                                     new Vector2(sprite.getWidth(), sprite.getHeight() ));
    }
    SoundHandler.getInstance().playSound("smallexplosion.wav");
  }
}
