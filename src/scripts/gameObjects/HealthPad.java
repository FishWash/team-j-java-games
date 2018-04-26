package scripts.gameObjects;

import scripts.GameWorld;
import scripts.utility.ClockListener;
import scripts.utility.Vector2;

import java.util.ArrayList;

public class HealthPad extends TriggerGameObject implements ClockListener {
  private int healAmount = 1;

  public HealthPad(Vector2 position, GameWorld.Player owner) {
    super(position);
    this.owner = owner;
    if (owner == GameWorld.Player.One) {
      sprite = GameWorld.loadSprite("Healthpad_blue.png");
    }
    else if (owner == GameWorld.Player.Two) {
      sprite = GameWorld.loadSprite("Healthpad_red.png");
    }
    trigger.setSize(new Vector2(sprite.getWidth(), sprite.getHeight()));
    renderingLayer = GameWorld.RenderingLayer.BackgroundGameObject;
  }

  public void update() {
    ArrayList<Damageable> overlappingFriendlyDamageables
            = GameWorld.findOverlappingFriendlyDamageables(trigger, owner);
    for (Damageable d : overlappingFriendlyDamageables) {
      d.heal(healAmount);
    }
  }

}
