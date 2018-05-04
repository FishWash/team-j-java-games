package scripts.weapons;

import scripts.gameObjects.projectiles.Projectile;
import scripts.gameObjects.projectiles.ShotgunProjectile;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;

public class ShotgunWeapon extends Weapon {

  public ShotgunWeapon() {
    super();
    shootDelay = 40;
    recoil = -1.2;
    ammo = 8;

    TankGameWorld.getInstance().loadSprite("Shell_light_strip60.png");
    TankGameWorld.getInstance().loadSound("shotgunshot.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = null;
    for (int i=0; i<8; i++) {
      projectile = (Projectile) TankGameWorld.getInstance().instantiate(new ShotgunProjectile(position, 256, owner));
      projectile.setRotation(rotation);
    }
    TankGameWorld.getInstance().playSound("shotgunshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "Shotgun";
  }
}
