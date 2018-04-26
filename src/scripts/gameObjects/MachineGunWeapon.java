package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.Vector2;

public class MachineGunWeapon extends Weapon {

  public MachineGunWeapon() {
    super();
    shootDelay = 10;
    recoil = -0.3;
    ammo = 50;

    GameWorld.loadSprite("Shell_light_strip60.png");
  }
  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    return (Projectile) GameWorld.instantiate(new MachineGunProjectile(200, owner));
  }
}
