package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.Projectile;
import tankGame.gameObjects.projectiles.ShellProjectile;
import utility.Vector2;

public class ShellWeapon extends Weapon {

  public ShellWeapon() {
    super();
    shootDelay = 50;
    recoil = -0.6;
    ammo = -1;

    TankGameWorld.getInstance().loadSprite("Shell_light_strip60.png");
    TankGameWorld.getInstance().loadSprite("Explosion_small_strip6.png");
    TankGameWorld.getInstance().loadSound("shellshot.wav");
    TankGameWorld.getInstance().loadSound("smallexplosion.wav");
  }

  @Override
  public boolean fire(Vector2 position, double rotation, TankGameWorld.Player owner) {
    if (shootTimer.isDone()) {
      instantiateProjectile(position, rotation, owner);
      shootTimer.set(shootDelay);
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new ShellProjectile(position, owner));
    projectile.setRotation(rotation);
    TankGameWorld.getInstance().playSound("shellshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return null;
  }
}
