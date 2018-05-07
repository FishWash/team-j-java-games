package tankGame.gameObjects.explosions;

import general.gameObjects.Damageable;
import general.gameObjects.TriggerGameObject;
import tankGame.TankGameWorld;
import tankGame.gameObjects.TankGameObject;
import utility.AnimatedSprite;
import utility.ClockListener;
import utility.Timer;
import utility.Vector2;

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
    if (lifeTimer.isDone()) {
      die();
    }
  }

  // Deal damage to damageables in trigger area.
  private void damageDamageables() {
    ArrayList<Damageable> damageables = TankGameWorld.getInstance()
                                          .findOverlappingDamageables(trigger);
    for (Damageable d : damageables) {
      if (d instanceof TankGameObject
              && (((TankGameObject)d).getOwner() != owner) || (owner == TankGameWorld.Player.Neutral)){
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
