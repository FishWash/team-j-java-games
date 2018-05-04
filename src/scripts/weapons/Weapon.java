package scripts.weapons;

import scripts.gameObjects.projectiles.Projectile;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Timer;
import scripts.utility.Vector2;

public abstract class Weapon
{
  protected int shootDelay;
  protected Timer shootTimer;
  protected double recoil;
  protected int ammo;

  public Weapon() {
    shootTimer = new Timer();
  }

  public boolean fire(Vector2 position, double rotation, TankGameWorld.Player owner) {
    if (shootTimer.isDone() && ammo > 0) {
      Projectile p = instantiateProjectile(position, rotation, owner);
      p.setRotation(rotation);
      ammo--;
      shootTimer.set(shootDelay);
      return true;
    }
    else {
      return false;
    }
  }

  protected abstract Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner);

  public int getAmmo() {
    return ammo;
  }
  public double getRecoil() {
    return recoil;
  }
  public abstract String getWeaponName();
}
