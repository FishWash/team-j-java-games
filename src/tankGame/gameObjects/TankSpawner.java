package tankGame.gameObjects;

import tankGame.TankGameWorld;
import utility.ClockListener;
import utility.Timer;
import utility.Vector2;

public class TankSpawner extends TankGameObject implements ClockListener {

  private Tank spawnedTank;
  private int lives = 3;
  private Timer spawnTimer = new Timer();
  private int spawnDelay = 128;
  private boolean spawnTimerStarted = false;

  public TankSpawner(Vector2 position, TankGameWorld.Player owner) {
    this.position = position;
    this.owner = owner;
    this.spawnedTank = spawnTank();
  }

  public void update() {
      if (!spawnedTank.isAlive()) {
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
    return (Tank) TankGameWorld.getInstance().instantiate(new Tank(this.position, this.owner));
  }
}
