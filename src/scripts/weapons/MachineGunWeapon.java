package scripts.weapons;

import scripts.gameObjects.projectiles.MachineGunProjectile;
import scripts.gameObjects.projectiles.Projectile;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class MachineGunWeapon extends Weapon {

  public MachineGunWeapon() {
    super();
    shootDelay = 5;
    recoil = -0.1;
    ammo = 50;

    GameWorld.getInstance().loadSprite("Shell_light_strip60.png");
    GameWorld.getInstance().loadSound("machinegunshot.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile projectile = (Projectile) GameWorld.instantiate(new MachineGunProjectile(position, 200, owner));
    projectile.setRotation(rotation);
    GameWorld.getInstance().playSound("machinegunshot.wav");
    return projectile;
  }
}
