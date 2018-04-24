package Scripts;

public abstract class Weapon
{
  protected int shootDelay;
  protected Timer shootTimer;
  protected double recoil;

  public Weapon() {
    shootTimer = new Timer();
  }

  public double getRecoil() {
    return recoil;
  }

  // Returns true if a shot was fired.
  public boolean shoot(Vector2 position, double rotation, GameWorld.Player owner) {
    if (shootTimer.isDone()) {
      instantiateProjectile(position, rotation, owner);
      shootTimer.set(shootDelay);
      return true;
    }
    else {
      return false;
    }
  }

  protected Projectile instantiateProjectile(Vector2 position, double rotation, GameWorld.Player owner) {
    Projectile p = (Projectile) GameWorld.instantiate(new Projectile(100, owner));
    p.setPosition(position);
    p.setRotation(rotation);
    return p;
  }
}
