package scripts.weapons;

import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class ShellWeapon extends Weapon
{
  public ShellWeapon() {
    super();
    shootDelay = 30;
    recoil = -0.4;
    ammo = 1;

    GameWorld.getInstance().loadSprite("Shell_light_strip60.png");
    GameWorld.getInstance().loadSprite("Explosion_small_strip6.png");
    GameWorld.getInstance().loadSound("shellshot.wav");
    GameWorld.getInstance().loadSound("smallexplosion.wav");
  }

  @Override
  public boolean fire(Vector2 position, double rotation, GameWorld.Player owner) {
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
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile projectile = (Projectile) GameWorld.instantiate(new ShellProjectile(position, owner));
    projectile.setRotation(rotation);
    GameWorld.getInstance().playSound("shellshot.wav");
    return projectile;
  }
}
