package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.Vector2;

public class DestructibleWall extends CollidableGameObject implements Damageable
{
  private int maxHealth = 15;
  private int health = maxHealth;

  public DestructibleWall(Vector2 position) {
    super(position);
    sprite = GameWorld.loadSprite("wall_destructible1.png");
    trigger.setSize(new Vector2(GameWorld.getInstance().TILE_SIZE, GameWorld.getInstance().TILE_SIZE));
    renderingLayer = GameWorld.RenderingLayer.Walls;
  }

  public  int getMaxHealth(){
    return maxHealth;
  }

  public int getHealth(){
    return health;
  }

  public void damage(int damageAmount) {
    health -= damageAmount;
    if (health <= 0) {
      die();
    }
    else if (health <= 5) {
      sprite = GameWorld.loadSprite("wall_destructible2.png");
    }
  }

  public void heal(int healAmount) {}
}