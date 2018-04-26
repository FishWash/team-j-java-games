package Scripts;

public class ShellProjectile extends Projectile {

  public ShellProjectile(int lifeTime, GameWorld.Player owner) {
    super(lifeTime, owner);
    moveSpeed = 5;
    setSprite("Shell_heavy_strip60.png");
  }

  @Override
  public void die() {
    if (alive) {
      GameWorld.instantiate(new Explosion(getCenterPosition(), GameWorld.Player.Neutral));
    }
    super.die();
  }
}
