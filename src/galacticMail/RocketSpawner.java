package galacticMail;

import galacticMail.gameObjects.Rocket;
import general.GamePanel;
import general.GameWorld;
import utility.Clock;
import utility.ClockListener;
import utility.Timer;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;


public class RocketSpawner implements ClockListener {

  private Vector2 spawnPosition;
  private Rocket spawnedRocket;
  private Timer spawnTimer = new Timer();
  private final int SPAWN_TIME = 32;
  private boolean isSpawning;

  private BufferedImage lifeSprite;

  public RocketSpawner(Vector2 spawnPosition) {
    this.spawnPosition = spawnPosition;
    Clock.getInstance().addClockListener(this);
    GalacticMailWorld galacticMailWorld = (GalacticMailWorld) GameWorld.getInstance();
    spawnedRocket = (Rocket) galacticMailWorld.instantiate(new Rocket(spawnPosition));

    lifeSprite = galacticMailWorld.loadSprite("Extra_rocket.png");
  }

  @Override
  public void update() {

    if (spawnedRocket != null && !spawnedRocket.isAlive()) {
      if (!isSpawning) {
        isSpawning = true;
        spawnTimer.set(SPAWN_TIME);
      }
      else if (spawnTimer.isDone()) {
        isSpawning = false;
        spawnRocket();
      }
    }

  }

  private void spawnRocket() {
    GalacticMailWorld galacticMailWorld = (GalacticMailWorld) GameWorld.getInstance();
    GalacticMailPanel galacticMailPanel = (GalacticMailPanel) GamePanel.getInstance();

    if (galacticMailPanel.getLives() > 0) {
      if (!galacticMailWorld.isGameOver()) {
        spawnedRocket = (Rocket) galacticMailWorld.instantiate(
                new Rocket(spawnPosition)
        );
        galacticMailPanel.loseLife();
      }
    }
    else {
      galacticMailWorld.setGameState(GalacticMailWorld.GameState.Defeat);
    }
  }

  public void drawLives(Graphics graphics) {
    int numLives = ( (GalacticMailPanel) GamePanel.getInstance() ).getLives();

    for (int i=0; i<numLives; i++) {
      graphics.drawImage(lifeSprite, i*lifeSprite.getWidth(), 0, null);
    }
  }
}
