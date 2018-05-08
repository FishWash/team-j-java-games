package galacticMail;

import galacticMail.gameObjects.Asteroid;
import galacticMail.gameObjects.Moon;
import galacticMail.gameObjects.Planet;
import galacticMail.gameObjects.Rocket;
import general.GamePanel;
import utility.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.awt.*;

public class GalacticLevelWorld extends GalacticMailWorld {

  RocketSpawner rocketSpawner;

  public GalacticLevelWorld(int level) {
    super(level);
  }

  @Override
  protected void initialize() {

    drawVariedBackground();
    spawnAsteroids();
    spawnMoons();
    spawnPlanet();
    rocketSpawner = new RocketSpawner( new Vector2(dimension.width/2,
                                                   dimension.height/2) );

    new Camera();
    new FlashingText();
    new Scoreboard();
    scoreboard = Scoreboard.readScoreboard("src/galacticMail/Scoreboard.txt");

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Pause);
    GamePanel.getInstance().setEscapeFunction(GamePanel.EscapeFunction.Pause);
  }

  @Override
  public void display(Graphics graphics) {
    graphics.drawImage(getCurrentImage(), 0, 0, null);
    Graphics2D graphics2D = (Graphics2D) graphics;

    rocketSpawner.drawLives(graphics);

    Camera.displayPoints(graphics2D, PointsHandler.getInstance().getPoints());
    Camera.displayLevel(graphics2D, level);

    if (gameState == GameState.Defeat){
      Camera.displayLoseScreen(graphics2D);
      FlashingText.drawFlashingText(graphics, "Press Space to Restart", 0.75);
    }
    if (gameState == GameState.Victory){
      Camera.displayWinScreen(graphics2D);
      FlashingText.drawFlashingText(graphics, "Press Space to Continue", 0.6);
    }
  }

  private void drawVariedBackground() {
    // Draw a different background based on level number.
    switch(level % 4) {
      case 0:
        drawBackground(loadSprite("Background4.png"));
        break;
      case 1:
        drawBackground(loadSprite("Background1.png"));
        break;
      case 2:
        drawBackground(loadSprite("Background2.png"));
        break;
      case 3:
        drawBackground(loadSprite("Background3.png"));
        break;
    }
  }

  private void spawnAsteroids() {
    // Number of asteroids and their speed based on level number.
    // Position and rotation are randomized.
    int numAsteroids = 6 + (int)Math.pow(level+1, 0.8);
    double asteroidSpeed = 0.6 + (level*0.3);

    for (int i=0; i<numAsteroids; i++) {
      Vector2 randomPosition = RandomNumberGenerator.getRandomPosition(
              0, 0, dimension.width, dimension.height );
      double randomRotation = RandomNumberGenerator.getRandomDouble(0, 360);

      Asteroid asteroid = (Asteroid)instantiate(new Asteroid(randomPosition,
                                                             randomRotation));
      asteroid.setMoveSpeed(asteroidSpeed);
    }
  }

  private void spawnMoons() {
    // Number of moons and their speed are based on level number.
    // Position and rotation are randomized.
    int numMoons = 3 + (int)Math.pow(level, 0.5);
    double moonSpeed = 0.7 + (level*0.1);

    for (int i=0; i<numMoons; i++) {
      Vector2 randomPosition = RandomNumberGenerator.getRandomPosition(
              0, 0, dimension.width, dimension.height );
      double randomRotation = RandomNumberGenerator.getRandomDouble(0, 360);

      Moon moon = (Moon)instantiate(new Moon(randomPosition,randomRotation));
      moon.setMoveSpeed(moonSpeed);
    }
  }

  private void spawnPlanet() {
    if (level % 2 == 0) {
      Vector2 randomPosition = RandomNumberGenerator.getRandomPosition(
              0, 0, dimension.width, dimension.height );
      double randomRotation = RandomNumberGenerator.getRandomDouble(0, 360);
      Planet planet = (Planet)instantiate(new Planet(randomPosition, randomRotation));
      double planetSpeed = 1.7 + (level*0.3);
      planet.setMoveSpeed(planetSpeed);
    }
  }
}
