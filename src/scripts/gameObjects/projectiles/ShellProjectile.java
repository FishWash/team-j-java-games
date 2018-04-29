package scripts.gameObjects.projectiles;

import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;
import scripts.gameObjects.explosions.ShellExplosion;

public class ShellProjectile extends Projectile {

  public ShellProjectile(Vector2 position, GameWorld.Player owner) {
    super(position, 200, owner);
    moveSpeed = 5;
    setSprite("Shell_heavy_strip60.png");
  }

  @Override
  public void die() {
    if (alive) {
      GameWorld.instantiate(new ShellExplosion(position, owner));
    }
    super.die();
  }
}
