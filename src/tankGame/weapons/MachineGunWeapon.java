package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.MachineGunProjectile;
import tankGame.gameObjects.projectiles.Projectile;
import utility.SoundHandler;
import utility.SpriteHandler;
import utility.Vector2;

public class MachineGunWeapon extends Weapon {

  public MachineGunWeapon() {
    super();
    shootDelay = 5;
    recoil = -0.2;
    ammo = 50;

    SpriteHandler.getInstance().loadSprite("Shell_light_strip60.png");
    SoundHandler.getInstance().loadSound("machinegunshot.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new MachineGunProjectile(position, 200, owner));
    projectile.setRotation(rotation);
    SoundHandler.getInstance().playSound("machinegunshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "Machine Gun";
  }
}
