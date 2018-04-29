package scripts.weapons;

import scripts.gameObjects.projectiles.FlamethrowerProjectile;
import scripts.gameObjects.projectiles.MachineGunProjectile;
import scripts.gameObjects.projectiles.Projectile;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class FlamethrowerWeapon extends Weapon {
  public FlamethrowerWeapon() {
    super();
    shootDelay = 4;
    recoil = -0.05;
    ammo = 150;

    GameWorld.getInstance().loadSprite("flamethrow.png");
    GameWorld.getInstance().loadSound("flame.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile projectile = (Projectile) GameWorld.instantiate(new FlamethrowerProjectile(position, 30, owner));
    projectile.setRotation(rotation);
    GameWorld.getInstance().playSound("flame.wav");
    return projectile;
  }
}
