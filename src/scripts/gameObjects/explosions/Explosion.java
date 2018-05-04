package scripts.gameObjects.explosions;

import scripts.gameObjects.TankGameObject;
import scripts.gameWorlds.TankGameWorld;
import scripts.gameObjects.TriggerGameObject;
import scripts.gameObjects.Damageable;
import scripts.utility.*;

import java.awt.*;
import java.util.ArrayList;

public abstract class Explosion extends TriggerGameObject implements ClockListener
{
  protected AnimatedSprite animatedSprite;
  protected int damage = 1;
  protected Timer lifeTimer = new Timer(32);

  public Explosion (Vector2 position, TankGameWorld.Player owner) {
    super(position);
    this.owner = owner;
    renderingLayer = TankGameWorld.RenderingLayer.ForegroundGameObject;
  }

  public void update() {
    if (alive) {
      damageDamageables();
      alive = false;
    }
  }

  private void damageDamageables() {
    ArrayList<Damageable> damageables = TankGameWorld.getInstance()
                                          .findOverlappingDamageables(trigger);
    for (Damageable d : damageables) {
      if (d instanceof TankGameObject
              && ((TankGameObject)d).getOwner() != owner) {
        d.damage(damage);
      }
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = animatedSprite.getCurrentSprite();
    super.drawSprite(graphics);
  }
}
