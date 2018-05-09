package tankGame.gameObjects;

import general.gameObjects.BoxCollidableGameObject;
import general.gameObjects.CornerBoxTrigger;
import general.gameObjects.Damageable;
import tankGame.TankGameWorld;
import utility.SoundHandler;
import utility.SpriteHandler;
import utility.Vector2;

import java.awt.*;

public class DestructibleWall extends BoxCollidableGameObject implements Damageable
{
  private int maxHealth = 12;
  private int health = maxHealth;

  public DestructibleWall(Vector2 position) {
    super(position);
    sprite = SpriteHandler.getInstance().loadSprite("wall_destructible1.png");
    trigger = new CornerBoxTrigger(this,
                                   new Vector2(TankGameWorld.getInstance().TILE_SIZE,
                                       TankGameWorld.getInstance().TILE_SIZE));
    renderingLayer = TankGameWorld.RenderingLayer.Walls;

    SoundHandler.getInstance().loadSound("wallhit.wav");
  }

  public  int getMaxHealth(){
    return maxHealth;
  }

  public int getHealth(){
    return health;
  }

  public void damage(int damageAmount) {
    health -= damageAmount;
    SoundHandler.getInstance().playSound("wallhit.wav");
    if (health <= 0) {
      die();
    }
    else {
      sprite = SpriteHandler.getInstance().loadSprite("wall_destructible2.png");
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