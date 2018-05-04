package tankGame.gameObjects.projectiles;

import tankGame.TankGameWorld;
import utility.RandomNumberGenerator;
import utility.Vector2;

public class ShotgunProjectile extends Projectile {

  public ShotgunProjectile(Vector2 position, int lifeTime, TankGameWorld.Player owner) {
    super(position, lifeTime, owner);
    moveSpeed = 12;
    damage = 7;
    setSprite("Shell_light_strip60.png");
  }

  @Override
  public void setRotation(double rotation) {
    this.rotation = rotation + RandomNumberGenerator.getSpread(25);
  }
}
