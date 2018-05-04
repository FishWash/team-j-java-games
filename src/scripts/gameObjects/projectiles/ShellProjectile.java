package scripts.gameObjects.projectiles;

import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;
import scripts.gameObjects.explosions.ShellExplosion;

public class ShellProjectile extends Projectile {

  public ShellProjectile(Vector2 position, TankGameWorld.Player owner) {
    super(position, 200, owner);
    moveSpeed = 5;
    setSprite("Shell_heavy_strip60.png");
  }

  @Override
  public void die() {
    if (alive) {
      TankGameWorld.getInstance().instantiate(new ShellExplosion(position, owner));
    }
    super.die();
  }
}
