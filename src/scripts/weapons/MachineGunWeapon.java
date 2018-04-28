package scripts.weapons;

import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class MachineGunWeapon extends Weapon {

  public MachineGunWeapon() {
    super();
    shootDelay = 0;
    recoil = -0.01;
    ammo = 500;

    GameWorld.getInstance().loadSprite("Shell_light_strip60.png");
    GameWorld.getInstance().loadSound("machinegunshot.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile projectile = (Projectile) GameWorld.instantiate(new MachineGunProjectile(position,200, owner));
    projectile.setRotation(rotation);
    GameWorld.getInstance().playSound("machinegunshot.wav");
    return projectile;
  }
}
