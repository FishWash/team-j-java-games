package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class SpeedPickup extends Pickup {

  private final int BOOST_DURATION = 512;

  public SpeedPickup(Vector2 position) {
    super(position, 2);
    GameWorld.getInstance().loadSound("pickupget.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.giveSpeedBoost(BOOST_DURATION);
    GameWorld.getInstance().playSound("pickupget.wav");
    die();
  }
}
