package Scripts;

public class ShellWeapon extends Weapon
{
  public ShellWeapon() {
    super();
    shootDelay = 30;
    recoil = -1.2;
  }

  @Override
  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile p = (Projectile) GameWorld.instantiate(new Projectile(200, owner));
    p.setPosition(position);
    p.setRotation(rotation);
    p.setMoveSpeed(4);
    p.setDamage(10);
    p.setSprite("Shell_heavy_strip60.png");
    return p;
  }
}
