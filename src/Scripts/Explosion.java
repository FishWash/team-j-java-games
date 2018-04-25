package Scripts;

import java.util.ArrayList;

public class Explosion extends TriggerGameObject implements ClockObserver
{
  protected MultiSprite multiSprite;
  protected int damage = 10;
  protected int animationIndex = 0;
  protected Timer animationTimer = new Timer();
  protected int animationFrameLength = 4;

  public Explosion (Vector2 position, GameWorld.Player owner) {
    super(position);
    multiSprite = new MultiSprite(GameWorld.loadSprite("Explosion_small_strip6.png"), 6);
    sprite = multiSprite.getSubSprite(0);
    if (sprite != null) {
      trigger = new BoxTrigger(this, new Vector2( sprite.getWidth(), sprite.getHeight() ));
      this.position = Vector2.subtractVectors(position, new Vector2(sprite.getWidth()/2, sprite.getHeight()/2));
    }
    this.owner = owner;
  }

  public void update() {
    if (alive) {
      damageDamageables();
      alive = false;
    }
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
    ArrayList<Damageable> damageables = GameWorld.findOverlappingEnemyDamageables(trigger, owner);
    for (Damageable d : damageables) {
      d.takeDamage(damage);
    }
  }
}
