package scripts.gameObjects;

import scripts.gameWorlds.TankGameWorld;
import scripts.utility.ClockListener;
import scripts.utility.Vector2;

import java.util.ArrayList;

public class HealthPad extends TriggerGameObject implements ClockListener {
  private int healAmount = 1;

  public HealthPad(Vector2 position, TankGameWorld.Player owner) {
    super(position);
    this.owner = owner;
    if (owner == TankGameWorld.Player.One) {
      sprite = TankGameWorld.getInstance().loadSprite("Healthpad_blue.png");
    }
    else if (owner == TankGameWorld.Player.Two) {
      sprite = TankGameWorld.getInstance().loadSprite("Healthpad_red.png");
    }
    trigger = new CenterBoxTrigger(this, new Vector2(sprite.getWidth(), sprite.getHeight()));
    renderingLayer = TankGameWorld.RenderingLayer.BackgroundGameObject;
  }

  public void update() {
    ArrayList<Damageable> overlappingFriendlyDamageables
            = TankGameWorld.getInstance().findOverlappingDamageables(trigger);
    for (Damageable d : overlappingFriendlyDamageables) {
      if (d instanceof TankGameObject && ((TankGameObject)d).getOwner() == owner)
      d.heal(healAmount);
    }
  }

}
