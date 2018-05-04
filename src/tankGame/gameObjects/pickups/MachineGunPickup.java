package tankGame.gameObjects.pickups;

import tankGame.TankGameWorld;
import tankGame.gameObjects.Tank;
import tankGame.weapons.MachineGunWeapon;
import utility.Vector2;

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
