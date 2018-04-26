package scripts.gameObjects;

public interface Damageable
{
  void takeDamage(int damageAmount);

  int getMaxHealth();

  int getHealth();
}
