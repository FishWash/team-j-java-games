package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.Vector2;
import scripts.weapons.MachineGunWeapon;

public class MachineGunPickup extends Pickup {
  public MachineGunPickup(Vector2 position) {
    super(position, 0);
    TankGameWorld.getInstance().loadSound("weaponequip.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.equip(new MachineGunWeapon());
    TankGameWorld.getInstance().playSound("weaponequip.wav");
    die();
  }
}
