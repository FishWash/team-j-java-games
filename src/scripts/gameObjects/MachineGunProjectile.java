package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.RandomNumberGenerator;

public class MachineGunProjectile extends Projectile {
  int damage = 5;

  public MachineGunProjectile(int lifeTime, GameWorld.Player owner) {
    super(lifeTime, owner);
    moveSpeed = 8;
    setSprite("Shell_light_strip60.png");
  }

  @Override
  protected void checkCollidables() {
    Collidable c = GameWorld.findOverlappingCollidable(trigger);
    if (c != null && (owner == GameWorld.Player.Neutral || ((GameObject) c).getOwner() != owner)) {
      if (c instanceof Damageable) {
        ((Damageable) c).damage(damage);
      }
      die();
    }
  }

  @Override
  public void setRotation(double rotation) {
    this.rotation = rotation + RandomNumberGenerator.getSpread(5);
  }
}
