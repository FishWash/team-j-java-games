package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.Vector2;

public class ShellWeapon extends Weapon
{
  public ShellWeapon() {
    super();
    shootDelay = 30;
    recoil = -0.4;
    ammo = 1;

    GameWorld.loadSprite("Shell_light_strip60.png");
    GameWorld.loadSprite("Explosion_small_strip6.png");
  }

  @Override
  public boolean fire(Vector2 position, double rotation, GameWorld.Player owner) {
    if (shootTimer.isDone()) {
      Projectile p = instantiateProjectile(position, rotation, owner);
      p.setPosition(position);
      p.setRotation(rotation);
      shootTimer.set(shootDelay);
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    return (Projectile) GameWorld.instantiate(new ShellProjectile(200, owner));
  }
}
