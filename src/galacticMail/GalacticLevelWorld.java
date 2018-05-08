package galacticMail;

import galacticMail.gameObjects.Asteroid;
import galacticMail.gameObjects.Moon;
import galacticMail.gameObjects.Rocket;
import general.GamePanel;
import general.GameWorld;
import utility.Camera;
import utility.FlashingText;
import utility.RandomNumberGenerator;
import utility.Vector2;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.awt.*;

public class GalacticLevelWorld extends GalacticMailWorld {

  public GalacticLevelWorld(int level) {
    super(level);
  }

  @Override
  protected void initialize() {
    // Draw Background based on level number.
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

    // Number of Asteroids and Moons and their speeds are determined by an
    // algorithm based on level number.
    int numAsteroids = 6 + (int)Math.pow(level+1, 1.1);
    double asteroidSpeed = 0.6 + (level*0.4);
    for (int i=0; i<numAsteroids; i++) {
      Asteroid asteroid = (Asteroid)instantiate(new Asteroid(
              RandomNumberGenerator.getRandomPosition(
                      0, 0, dimension.width, dimension.height ),
              RandomNumberGenerator.getRandomDouble(0, 360)
      ));
      asteroid.setMoveSpeed(asteroidSpeed);
    }
    double moonSpeed = 0.7 + (level*0.1);
    int numMoons = 3 + (int)Math.pow(level, 0.9);
    for (int i=0; i<numMoons; i++) {
      Moon moon = (Moon)instantiate(new Moon(
              RandomNumberGenerator.getRandomPosition(
                      0, 0, dimension.width, dimension.height ),
              RandomNumberGenerator.getRandomDouble(0, 360)
      ));
      moon.setMoveSpeed(moonSpeed);
    }
    System.out.println("Level " + level + ":");
    System.out.println("  " + numAsteroids + " Asteroids");
    System.out.println("  " + numMoons + " Moons");
    System.out.println("  Asteroid speed: " + asteroidSpeed);
    System.out.println("  Moon speed: " + moonSpeed);

//    instantiate(new Rocket(new Vector2(dimension.width/2, dimension.height/2)));
    new RocketSpawner( new Vector2(dimension.width/2, dimension.height/2) );

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Pause);
    GamePanel.getInstance().setEscapeFunction(GamePanel.EscapeFunction.Pause);
  }

  @Override
  public void display(Graphics graphics) {
    graphics.drawImage(getCurrentImage(), 0, 0, null);
    Graphics2D graphics2D = (Graphics2D) graphics;

    Camera.displayPoints(graphics2D, PointsHandler.getInstance().getPoints());
    Camera.displayLevel(graphics2D, level);

    if(gameState == GameState.Defeat){
      Camera.displayLoseScreen(graphics2D);
      FlashingText.drawFlashingText(graphics, "Press Space to Restart", 0.6);
    }
    if (gameState == GameState.Victory) {
      Camera.displayWinScreen(graphics2D);
      FlashingText.drawFlashingText(graphics, "Press Space to Continue", 0.6);
    }
  }
}
