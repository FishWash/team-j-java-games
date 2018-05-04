package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;
import scripts.weapons.FlamethrowerWeapon;

public class FlamethrowerPickup extends Pickup {
  public FlamethrowerPickup(Vector2 position) {
    super(position, 6);
    TankGameWorld.getInstance().loadSound("weaponequip.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.equip(new FlamethrowerWeapon());
    TankGameWorld.getInstance().playSound("weaponequip.wav");
    die();
  }
}
