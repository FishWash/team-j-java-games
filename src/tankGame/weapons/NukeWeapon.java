package tankGame.weapons;

import tankGame.TankGameWorld;
import tankGame.gameObjects.projectiles.NukeProjectile;
import tankGame.gameObjects.projectiles.Projectile;
import utility.SoundHandler;
import utility.SpriteHandler;
import utility.Vector2;

public class NukeWeapon extends Weapon {

  public NukeWeapon() {
    super();
    shootDelay = 100;
    recoil = -1.5;
    ammo = 1;

    SpriteHandler.getInstance().loadSprite("Shell_nuclear_strip60.png");
    SpriteHandler.getInstance().loadSprite("nuke_explosion_strip32.png");
    SoundHandler.getInstance().loadSound("nukeshellshot.wav");
    SoundHandler.getInstance().loadSound("nuke_explosion.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, TankGameWorld.Player owner) {
    Projectile projectile = (Projectile) TankGameWorld.getInstance().instantiate(new NukeProjectile(position, owner));
    projectile.setRotation(rotation);
    SoundHandler.getInstance().playSound("nukeshellshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "This looks dangerous";
  }
}
