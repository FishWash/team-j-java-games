package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.Projectile;
import tankGame.gameObjects.projectiles.ShellProjectile;
import utility.SoundHandler;
import utility.SpriteHandler;
import utility.Vector2;

public class ShellWeapon extends Weapon {

  public ShellWeapon() {
    super();
    shootDelay = 50;
    recoil = -0.6;
    ammo = -1;

    SpriteHandler.getInstance().loadSprite("Shell_light_strip60.png");
    SpriteHandler.getInstance().loadSprite("Explosion_small_strip6.png");
    SoundHandler.getInstance().loadSound("shellshot.wav");
    SoundHandler.getInstance().loadSound("smallexplosion.wav");
  }

  @Override
  public boolean fire(Vector2 position, double rotation, TankGameWorld.Player owner) {
    if (shootTimer.isDone()) {
      instantiateProjectile(position, rotation, owner);
      shootTimer.set(shootDelay);
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new ShellProjectile(position, owner));
    projectile.setRotation(rotation);
    SoundHandler.getInstance().playSound("shellshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return null;
  }
}
