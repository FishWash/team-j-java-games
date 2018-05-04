package general.gameObjects;

public interface Damageable
{
  void damage(int damageAmount);

  void heal(int healAmount);

  int getMaxHealth();

  int getHealth();
}
