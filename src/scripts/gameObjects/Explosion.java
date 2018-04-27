package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.*;

import java.util.ArrayList;

public abstract class Explosion extends BoxTriggerGameObject implements ClockListener
{
  protected MultiSprite multiSprite;
  protected int damage = 1;
  protected int animationIndex = 0;
  protected Timer animationTimer = new Timer();
  protected int animationFrameLength = 4;

  public Explosion (Vector2 position, GameWorld.Player owner) {
    super(position);
    this.owner = owner;
    renderingLayer = GameWorld.RenderingLayer.ForegroundGameObject;
  }

  public void update() {
    if (alive) {
      damageDamageables();
      alive = false;
    }

    animate();
  }

  private void animate()  {
    if (animationIndex < multiSprite.getNumSubSprites()) {
      if (animationTimer.isDone()) {
        sprite = multiSprite.getSubSprite(animationIndex);
        animationIndex++;
        animationTimer.set(animationFrameLength);
      }
    }
    else {
      sprite = null;
      die();
    }
  }

  private void damageDamageables() {
    ArrayList<Damageable> damageables = GameWorld.findOverlappingEnemyDamageables(boxTrigger, owner);
    for (Damageable d : damageables) {
      d.damage(damage);
    }
  }
}
