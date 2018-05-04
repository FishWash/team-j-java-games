package tankGame.gameObjects.projectiles;

import tankGame.TankGameWorld;
import tankGame.gameObjects.explosions.ShellExplosion;
import utility.Vector2;

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
