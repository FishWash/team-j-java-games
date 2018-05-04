package scripts.gameObjects.projectiles;

import scripts.gameObjects.Collidable;
import scripts.gameObjects.Damageable;
import scripts.gameObjects.GameObject;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.RandomNumberGenerator;
import scripts.utility.Vector2;

import java.awt.*;

public class FlamethrowerProjectile extends Projectile {

  public FlamethrowerProjectile(Vector2 position, int lifeTime, TankGameWorld.Player owner) {
    super(position, lifeTime, owner);
    moveSpeed = 5;
    damage = 4;
    sprite = TankGameWorld.getInstance().loadSprite("flamethrow.png");
  }

  @Override
  public void setRotation(double rotation) {
    this.rotation = rotation + RandomNumberGenerator.getSpread(20);
  }

  @Override
  public void drawSprite(Graphics graphics) {
    if (sprite != null) {
      double xPos = position.x - sprite.getWidth()/2;
      double yPos = position.y - sprite.getHeight()/2;
      graphics.drawImage(sprite, (int)xPos, (int)yPos, null);
    }
  }
}
