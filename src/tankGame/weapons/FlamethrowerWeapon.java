package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.FlamethrowerProjectile;
import tankGame.gameObjects.projectiles.Projectile;
import utility.SoundHandler;
import utility.SpriteHandler;
import utility.Vector2;

public class FlamethrowerWeapon extends Weapon {
  public FlamethrowerWeapon() {
    super();
    shootDelay = 4;
    recoil = -0.12;
    ammo = 100;

    SpriteHandler.getInstance().loadSprite("flamethrow.png");
    SoundHandler.getInstance().loadSound("flame.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new FlamethrowerProjectile(position, 30, owner));
    projectile.setRotation(rotation);
    SoundHandler.getInstance().playSound("flame.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "Flamethrower";
  }
}
