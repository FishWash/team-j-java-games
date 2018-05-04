package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.NukeProjectile;
import tankGame.gameObjects.projectiles.Projectile;
import utility.Vector2;

public class NukeWeapon extends Weapon {

  public NukeWeapon() {
    super();
    shootDelay = 100;
    recoil = -1.5;
    ammo = 1;

    TankGameWorld.getInstance().loadSprite("Shell_nuclear_strip60.png");
    TankGameWorld.getInstance().loadSprite("nuke_explosion_strip32.png");
    TankGameWorld.getInstance().loadSound("nukeshellshot.wav");
    TankGameWorld.getInstance().loadSound("nuke_explosion.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new NukeProjectile(position, owner));
    projectile.setRotation(rotation);
    TankGameWorld.getInstance().playSound("nukeshellshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "This looks dangerous";
  }
}
