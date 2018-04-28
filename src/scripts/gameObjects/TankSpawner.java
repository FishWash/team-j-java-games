package scripts.gameObjects;

import scripts.GameWorld;
import scripts.gameObjects.GameObject;
import scripts.gameObjects.Tank;
import scripts.utility.ClockListener;
import scripts.utility.PlayerCamera;
import scripts.utility.Timer;
import scripts.utility.Vector2;

public class TankSpawner extends GameObject implements ClockListener {

  private Tank spawnedTank;
  private boolean spawnTimerStarted = false;
  private Timer spawnTimer = new Timer();
  private int spawnDelay = 128;
  private int lives = 1;

  public TankSpawner(Vector2 position, GameWorld.Player owner) {
    this.position = position;
    this.owner = owner;
    this.spawnedTank = spawnTank();
  }

  public void update() {
    if(lives == 0) {
      if (!spawnedTank.getAlive()) {
        if (!spawnTimerStarted) {
          spawnTimer.set(spawnDelay);
          spawnTimerStarted = true;
        } else if (spawnTimer.isDone()) {
          spawnedTank = spawnTank();
          spawnTimerStarted = false;
          lives--;
        }
      }
    }
  }

  public int getLives(){
    return lives;
  }

  public Tank spawnTank() {
    return (Tank) GameWorld.instantiate(new Tank(this.position, this.owner));
  }
}
