package scripts.weapons;

import scripts.gameObjects.projectiles.NukeProjectile;
import scripts.gameObjects.projectiles.Projectile;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class NukeWeapon extends Weapon {

  public NukeWeapon() {
    super();
    shootDelay = 100;
    recoil = -1.5;
    ammo = 1;

    GameWorld.getInstance().loadSprite("Shell_nuclear_strip60.png");
    GameWorld.getInstance().loadSprite("nuke_explosion_strip32.png");
    GameWorld.getInstance().loadSound("nukeshellshot.wav");
    GameWorld.getInstance().loadSound("nuke_explosion.wav");
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile projectile = (Projectile) GameWorld.instantiate(new NukeProjectile(position, owner));
    projectile.setRotation(rotation);
    GameWorld.getInstance().playSound("nukeshellshot.wav");
    return projectile;
  }

  @Override
  public String getWeaponName() {
    return "This looks dangerous";
  }
}
