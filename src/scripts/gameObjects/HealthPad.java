package scripts.gameObjects;

import scripts.gameWorlds.GameWorld;
import scripts.utility.ClockListener;
import scripts.utility.Vector2;

import java.util.ArrayList;

public class HealthPad extends BoxTriggerGameObject implements ClockListener {
  private int healAmount = 1;

  public HealthPad(Vector2 position, GameWorld.Player owner) {
    super(position);
    this.owner = owner;
    if (owner == GameWorld.Player.One) {
      sprite = GameWorld.getInstance().loadSprite("Healthpad_blue.png");
    }
    else if (owner == GameWorld.Player.Two) {
      sprite = GameWorld.getInstance().loadSprite("Healthpad_red.png");
    }
    boxTrigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    renderingLayer = GameWorld.RenderingLayer.BackgroundGameObject;
  }

  public void update() {
    ArrayList<Damageable> overlappingFriendlyDamageables
            = GameWorld.findOverlappingFriendlyDamageables(boxTrigger, owner);
    for (Damageable d : overlappingFriendlyDamageables) {
      d.heal(healAmount);
    }
  }

}
