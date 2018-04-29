package scripts.weapons;

import scripts.gameObjects.projectiles.Projectile;
import scripts.gameObjects.projectiles.ShotgunProjectile;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class ShotgunWeapon extends Weapon {

  private final String SPRITE_FILE = "Shell_light_strip60.png";
  private final String SOUND_FILE = "shotgunshot.wav";
  private final int NUM_SHOTS = 5;

  public ShotgunWeapon() {
    super();
    shootDelay = 40;
    recoil = -0.7;
    ammo = 12;

    GameWorld.getInstance().loadSprite(SPRITE_FILE);
    GameWorld.getInstance().loadSound(SOUND_FILE);
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile projectile = null;
    for (int i=0; i<NUM_SHOTS; i++) {
      projectile = (Projectile) GameWorld.instantiate(new ShotgunProjectile(position, 20, owner));
      projectile.setRotation(rotation);
    }
    GameWorld.getInstance().playSound(SOUND_FILE);
    return projectile;
  }
}
