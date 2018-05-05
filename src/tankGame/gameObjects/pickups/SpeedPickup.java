package tankGame.gameObjects.pickups;

import tankGame.TankGameWorld;
import tankGame.gameObjects.Tank;
import utility.Vector2;

public class SpeedPickup extends Pickup {

  private final int BOOST_DURATION = 512;

  public SpeedPickup(Vector2 position) {
    super(position, 2);
    TankGameWorld.getInstance().loadSound("pickupget.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.giveSpeedBoost(BOOST_DURATION);
    TankGameWorld.getInstance().playSound("pickupget.wav");
    die();
  }
}