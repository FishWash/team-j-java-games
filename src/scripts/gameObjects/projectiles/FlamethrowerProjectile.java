package scripts.gameObjects.projectiles;

import scripts.gameObjects.Collidable;
import scripts.gameObjects.Damageable;
import scripts.gameObjects.GameObject;
import scripts.gameWorlds.GameWorld;
import scripts.utility.RandomNumberGenerator;
import scripts.utility.Vector2;

import java.awt.*;

public class FlamethrowerProjectile extends Projectile {
  int damage = 4;

  public FlamethrowerProjectile(Vector2 position, int lifeTime, GameWorld.Player owner) {
    super(position, lifeTime, owner);
    moveSpeed = 5;
    sprite = GameWorld.getInstance().loadSprite("flamethrow.png");
  }

  @Override
  protected void checkCollidables() {
    Collidable c = GameWorld.findOverlappingCollidable(boxTrigger);
    if (c != null && (owner == GameWorld.Player.Neutral || ((GameObject) c).getOwner() != owner)) {
      if (c instanceof Damageable) {
        ((Damageable) c).damage(damage);
      }
      die();
    }
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
