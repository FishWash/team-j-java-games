package scripts.weapons;

import scripts.gameObjects.projectiles.FlamethrowerProjectile;
import scripts.gameObjects.projectiles.Projectile;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;

public class FlamethrowerWeapon extends Weapon {
  public FlamethrowerWeapon() {
    super();
    shootDelay = 4;
    recoil = -0.12;
    ammo = 100;

    TankGameWorld.getInstance().loadSprite("flamethrow.png");
    TankGameWorld.getInstance().loadSound("flame.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new FlamethrowerProjectile(position, 30, owner));
    projectile.setRotation(rotation);
    TankGameWorld.getInstance().playSound("flame.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "Flamethrower";
  }
}
