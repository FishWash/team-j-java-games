package scripts.gameObjects;

import scripts.gameObjects.pickups.*;
import scripts.gameWorlds.GameWorld;
import scripts.utility.ClockListener;
import scripts.utility.RandomNumberGenerator;
import scripts.utility.Timer;
import scripts.utility.Vector2;

import java.util.Random;

public class PickupSpawner extends GameObject implements ClockListener {

  private Pickup spawnedPickup;
  private boolean spawnTimerStarted = false;
  private Timer spawnTimer = new Timer();
  private int spawnDelay = 512;

  public PickupSpawner(Vector2 position) {
    this.position = position;
    this.spawnedPickup = spawnPickup();
  }

  public void update() {
    if (!spawnedPickup.getAlive()) {
      if (!spawnTimerStarted) {
        spawnTimer.set(spawnDelay);
        spawnTimerStarted = true;
      } else if (spawnTimer.isDone()) {
        spawnedPickup = spawnPickup();
        spawnTimerStarted = false;
      }
    }
  }

  private Pickup spawnPickup() {
    Pickup pickup;
    switch(RandomNumberGenerator.getRandomInt(0, 3)) {
      case 1:
        pickup = new MachineGunPickup(this.position);
        break;
      case 2:
        pickup = new FlamethrowerPickup(this.position);
        break;
      case 3:
        pickup = new SpeedPickup(this.position);
        break;
      default:
        pickup = new HealthPickup(this.position);
    }
    return (Pickup) GameWorld.instantiate(pickup);
  }
}
