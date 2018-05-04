package tankGame.gameObjects.pickups;

import tankGame.TankGameWorld;
import tankGame.gameObjects.Tank;
import utility.Vector2;

public class HealthPickup extends Pickup {

  private int healAmount = 50;

  public HealthPickup(Vector2 position) {
    super(position, 7);
    TankGameWorld.getInstance().loadSound("pickupget.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.heal(healAmount);
    TankGameWorld.getInstance().playSound("pickupget.wav");
    die();
  }
}
