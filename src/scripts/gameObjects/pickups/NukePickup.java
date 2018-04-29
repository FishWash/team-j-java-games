package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;
import scripts.weapons.NukeWeapon;

public class NukePickup extends Pickup {
  public NukePickup(Vector2 position) {
    super(position, 8);
    GameWorld.getInstance().loadSound("weaponequip.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.equip(new NukeWeapon());
    GameWorld.getInstance().playSound("weaponequip.wav");
    die();
  }
}
