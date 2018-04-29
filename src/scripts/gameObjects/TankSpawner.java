package scripts.gameObjects;

import scripts.gameWorlds.GameWorld;
import scripts.utility.ClockListener;
import scripts.utility.Timer;
import scripts.utility.Vector2;

public class TankSpawner extends GameObject implements ClockListener {

  private Tank spawnedTank;
  private boolean spawnTimerStarted = false;
  private Timer spawnTimer = new Timer();
  private int spawnDelay = 128;
  private int lives = 3;

  public TankSpawner(Vector2 position, GameWorld.Player owner) {
    this.position = position;
    this.owner = owner;
    this.spawnedTank = spawnTank();
  }

  public void update() {
      if (!spawnedTank.getAlive()) {
        if (!spawnTimerStarted) {
          lives--;
          spawnTimer.set(spawnDelay);
          spawnTimerStarted = true;
        } else if (spawnTimer.isDone() && lives > 0) {
          spawnedTank = spawnTank();
          spawnTimerStarted = false;
        }
      }
  }

  public int getLives(){
    return lives;
  }

  private Tank spawnTank() {
    return (Tank) GameWorld.instantiate(new Tank(this.position, this.owner));
  }
}
