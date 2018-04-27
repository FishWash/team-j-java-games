package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.Vector2;

public class ShellProjectile extends Projectile {

  public ShellProjectile(Vector2 position, int lifeTime, GameWorld.Player owner) {
    super(position, lifeTime, owner);
    moveSpeed = 5;
    setSprite("Shell_heavy_strip60.png");
  }

  @Override
  public void die() {
    if (alive) {
      GameWorld.instantiate(new ShellExplosion(position, GameWorld.Player.Neutral));
    }
    super.die();
  }
}
