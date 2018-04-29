package scripts.gameObjects.pickups;

import scripts.gameObjects.Tank;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Vector2;
import scripts.weapons.FlamethrowerWeapon;

public class FlamethrowerPickup extends Pickup {
  public FlamethrowerPickup(Vector2 position) {
    super(position, 6);
    GameWorld.getInstance().loadSound("weaponequip.wav");
  }

  @Override
  public void activatePickup(Tank tank) {
    tank.equip(new FlamethrowerWeapon());
    GameWorld.getInstance().playSound("weaponequip.wav");
    die();
  }
}
