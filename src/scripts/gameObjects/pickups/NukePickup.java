package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;
import scripts.weapons.NukeWeapon;

public class NukePickup extends Pickup {
  public NukePickup(Vector2 position) {
    super(position, 8);
    TankGameWorld.getInstance().loadSound("weaponequip.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.equip(new NukeWeapon());
    TankGameWorld.getInstance().playSound("weaponequip.wav");
    die();
  }
}
