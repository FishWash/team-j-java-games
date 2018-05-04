package scripts.gameObjects;

import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;

import java.awt.*;

public class DestructibleWall extends BoxCollidableGameObject implements Damageable
{
  private int maxHealth = 12;
  private int health = maxHealth;

  public DestructibleWall(Vector2 position) {
    super(position);
    sprite = TankGameWorld.getInstance().loadSprite("wall_destructible1.png");
    trigger = new CornerBoxTrigger(this,
                           new Vector2(TankGameWorld.getInstance().TILE_SIZE,
                                       TankGameWorld.getInstance().TILE_SIZE));
    renderingLayer = TankGameWorld.RenderingLayer.Walls;

    TankGameWorld.getInstance().loadSound("wallhit.wav");
  }

  public  int getMaxHealth(){
    return maxHealth;
  }

  public int getHealth(){
    return health;
  }

  public void damage(int damageAmount) {
    health -= damageAmount;
    TankGameWorld.getInstance().playSound("wallhit.wav");
    if (health <= 0) {
      die();
    }
    else {
      sprite = TankGameWorld.getInstance().loadSprite("wall_destructible2.png");
    }
  }

  public void heal(int healAmount) {}

  @Override
  public void drawSprite(Graphics graphics) {
    if (sprite != null) {
      double xPos = position.x;
      double yPos = position.y;
      graphics.drawImage(sprite, (int)xPos, (int)yPos, null);
    }
  }
}