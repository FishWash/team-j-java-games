package scripts.gameObjects.projectiles;

import scripts.gameObjects.explosions.NukeExplosion;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class NukeProjectile extends Projectile {

  public NukeProjectile(Vector2 position, GameWorld.Player owner) {
    super(position, 200, owner);
    moveSpeed = 5;
    setSprite("Shell_nuclear_strip60.png");
  }

  @Override
  public void die() {
    if (alive) {
      GameWorld.instantiate(new NukeExplosion(position));
    }
    super.die();
  }
}
