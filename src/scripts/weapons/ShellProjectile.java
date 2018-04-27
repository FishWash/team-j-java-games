package scripts.weapons;

import scripts.GameWorld;
import scripts.utility.Vector2;

public class ShellProjectile extends Projectile {

  public ShellProjectile(Vector2 position, GameWorld.Player owner) {
    super(position, 200, owner);
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
