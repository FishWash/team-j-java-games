package Scripts;

public class ShellWeapon extends Weapon
{
  public ShellWeapon() {
    super();
    shootDelay = 30;
    recoil = -0.4;
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile p = (Projectile) GameWorld.instantiate(new ShellProjectile(200, owner));
    p.setPosition(position);
    p.setRotation(rotation);
    return p;
  }
}
