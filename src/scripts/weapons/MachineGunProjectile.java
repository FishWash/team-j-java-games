package scripts.weapons;

import scripts.GameWorld;
import scripts.Collidable;
import scripts.Damageable;
import scripts.gameObjects.GameObject;
import scripts.utility.RandomNumberGenerator;
import scripts.utility.Vector2;

public class MachineGunProjectile extends Projectile {
  int damage = 5;

  public MachineGunProjectile(Vector2 position, int lifeTime, GameWorld.Player owner) {
    super(position, lifeTime, owner);
    moveSpeed = 8;
    setSprite("Shell_light_strip60.png");
  }

  @Override
  protected void checkCollidables() {
    Collidable c = GameWorld.findOverlappingCollidable(boxTrigger);
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
