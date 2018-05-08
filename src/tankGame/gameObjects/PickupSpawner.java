package tankGame.gameObjects;

import general.gameObjects.GameObject;
import tankGame.TankGameWorld;
import tankGame.gameObjects.pickups.*;
import utility.ClockListener;
import utility.RandomNumberGenerator;
import utility.Timer;
import utility.Vector2;

public class PickupSpawner extends GameObject implements ClockListener {

  private Pickup spawnedPickup;
  private boolean spawnTimerStarted = false;
  private Timer spawnTimer = new Timer();
  private int spawnDelay = 1024;
//  private int spawnDelay = 128;

  public PickupSpawner(Vector2 position) {
    this.position = position;
    this.spawnedPickup = spawnPickup();
  }

  public void update() {
    if (!spawnedPickup.isAlive()) {
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
    int n = RandomNumberGenerator.getRandomInt(1, 24);
    boolean noSurprise = (n != 24);

    if (noSurprise) {
      switch(RandomNumberGenerator.getRandomInt(1, 4)) {
        case 1:
          pickup = new MachineGunPickup(this.position);
          break;
        case 2:
          pickup = new FlamethrowerPickup(this.position);
          break;
        case 3:
          pickup = new SpeedPickup(this.position);
          break;
        case 4:
          pickup = new ShotgunPickup(this.position);
          break;
        default:
          pickup = new HealthPickup(this.position);
      }
      return (Pickup) TankGameWorld.getInstance().instantiate(pickup);
    }
    else {
      return (Pickup) TankGameWorld.getInstance().instantiate(new NukePickup(this.position));
    }
  }
}
