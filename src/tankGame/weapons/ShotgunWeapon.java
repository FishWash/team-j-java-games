package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.Projectile;
import tankGame.gameObjects.projectiles.ShotgunProjectile;
import utility.SoundHandler;
import utility.SpriteHandler;
import utility.Vector2;

public class ShotgunWeapon extends Weapon {

  public ShotgunWeapon() {
    super();
    shootDelay = 40;
    recoil = -1.2;
    ammo = 8;

    SpriteHandler.getInstance().loadSprite("Shell_light_strip60.png");
    SoundHandler.getInstance().loadSound("shotgunshot.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = null;
    for (int i=0; i<8; i++) {
      projectile = (Projectile) TankGameWorld.getInstance().instantiate(new ShotgunProjectile(position, 256, owner));
      projectile.setRotation(rotation);
    }
    SoundHandler.getInstance().playSound("shotgunshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "Shotgun";
  }
}
