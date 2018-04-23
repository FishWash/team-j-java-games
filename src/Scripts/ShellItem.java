package Scripts;

public class ShellItem extends Item
{
  public ShellItem() {
    super();
    shootDelay = 30;
    recoil = -1.3;
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
