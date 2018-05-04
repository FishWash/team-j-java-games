package scripts.weapons;

import scripts.gameObjects.projectiles.MachineGunProjectile;
import scripts.gameObjects.projectiles.Projectile;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;

public class MachineGunWeapon extends Weapon {

  public MachineGunWeapon() {
    super();
    shootDelay = 5;
    recoil = -0.2;
    ammo = 50;

    TankGameWorld.getInstance().loadSprite("Shell_light_strip60.png");
    TankGameWorld.getInstance().loadSound("machinegunshot.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new MachineGunProjectile(position, 200, owner));
    projectile.setRotation(rotation);
    TankGameWorld.getInstance().playSound("machinegunshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "Machine Gun";
  }
}
