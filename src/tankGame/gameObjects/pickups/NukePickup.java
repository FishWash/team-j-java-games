package tankGame.gameObjects.pickups;

import tankGame.TankGameWorld;
import tankGame.gameObjects.Tank;
import tankGame.weapons.NukeWeapon;
import utility.SoundHandler;
import utility.Vector2;

public class NukePickup extends Pickup {
  public NukePickup(Vector2 position) {
    super(position, 8);
    SoundHandler.getInstance().loadSound("weaponequip.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.equip(new NukeWeapon());
    SoundHandler.getInstance().playSound("weaponequip.wav");
    die();
  }
}
