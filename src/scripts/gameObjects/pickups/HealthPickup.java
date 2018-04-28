package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.utility.Vector2;

public class HealthPickup extends Pickup {

  private int healAmount = 50;

  public HealthPickup(Vector2 position) {
    super(position, 7);
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.heal(healAmount);
//    die();
  }
}
