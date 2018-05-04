package tankGame.gameObjects.projectiles;

import tankGame.TankGameWorld;
import utility.RandomNumberGenerator;
import utility.Vector2;

public class MachineGunProjectile extends Projectile {

  public MachineGunProjectile(Vector2 position, int lifeTime, TankGameWorld.Player owner) {
    super(position, lifeTime, owner);
    moveSpeed = 8;
    damage = 5;
    setSprite("Shell_light_strip60.png");
  }

  @Override
  public void setRotation(double rotation) {
    this.rotation = rotation + RandomNumberGenerator.getSpread(10);
  }
}
