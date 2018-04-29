package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;

public class SpeedPickup extends Pickup {

  private int duration = 256;

  public SpeedPickup(Vector2 position) {
    super(position, 2);
    GameWorld.getInstance().loadSound("pickupget.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.giveSpeedBoost(1024);
    GameWorld.getInstance().playSound("pickupget.wav");
    die();
  }
}
