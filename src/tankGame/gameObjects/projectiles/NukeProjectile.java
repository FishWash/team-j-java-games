package tankGame.gameObjects.projectiles;

import tankGame.TankGameWorld;
import tankGame.gameObjects.explosions.NukeExplosion;
import utility.Vector2;

public class NukeProjectile extends Projectile {

  public NukeProjectile(Vector2 position, TankGameWorld.Player owner) {
    super(position, 200, owner);
    moveSpeed = 5;
    setSprite("Shell_nuclear_strip60.png");
  }

  @Override
  public void die() {
    if (alive) {
      TankGameWorld.getInstance().instantiate(new NukeExplosion(position));
    }
    super.die();
  }
}
