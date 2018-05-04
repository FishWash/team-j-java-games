package tankGame.gameObjects.pickups;

import tankGame.TankGameWorld;
import tankGame.gameObjects.Tank;
import tankGame.weapons.FlamethrowerWeapon;
import utility.Vector2;

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
