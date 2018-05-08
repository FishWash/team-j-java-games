package galacticMail;

import galacticMail.gameObjects.Rocket;
import general.GamePanel;
import general.GameWorld;
import utility.Clock;
import utility.ClockListener;
import utility.Timer;
import utility.Vector2;


public class RocketSpawner implements ClockListener {

  private Vector2 spawnPosition;
  private Rocket spawnedRocket;
  private Timer spawnTimer = new Timer();
  private final int SPAWN_TIME = 32;

  public RocketSpawner(Vector2 spawnPosition) {
    this.spawnPosition = spawnPosition;
    Clock.getInstance().addClockListener(this);
    spawnRocket();
  }

  @Override
  public void update() {
    GalacticMailWorld galacticMailWorld = (GalacticMailWorld) GameWorld.getInstance();
    GalacticMailPanel galacticMailPanel = (GalacticMailPanel) GamePanel.getInstance();

    if (spawnedRocket != null && !spawnedRocket.getAlive()
            && spawnTimer.isDone()) {
      if (galacticMailPanel.getLives() > 0) {
        if (!galacticMailWorld.isGameOver())
        spawnRocket();
      }
      else {
        galacticMailWorld.setGameState(GalacticMailWorld.GameState.Defeat);
      }
    }
  }

  private void spawnRocket() {
    GalacticMailWorld galacticMailWorld = (GalacticMailWorld) GameWorld.getInstance();
    GalacticMailPanel galacticMailPanel = (GalacticMailPanel) GamePanel.getInstance();

    spawnedRocket = (Rocket) galacticMailWorld.instantiate(
            new Rocket(spawnPosition)
    );
    spawnTimer.set(SPAWN_TIME);
    if (!galacticMailWorld.isGameOver()) {
      galacticMailPanel.loseLife();
    }
  }
}
