package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.Vector2;

public class ShellWeapon extends Weapon
{
  public ShellWeapon() {
    super();
    shootDelay = 30;
    recoil = -0.4;

    GameWorld.loadSprite("Shell_heavy_strip60.png");
    GameWorld.loadSprite("Explosion_small_strip6.png");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile p = (Projectile) GameWorld.instantiate(new ShellProjectile(200, owner));
    p.setPosition(position);
    p.setRotation(rotation);
    return p;
  }
}
