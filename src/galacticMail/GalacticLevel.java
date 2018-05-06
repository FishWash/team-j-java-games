package galacticMail;

import galacticMail.gameObjects.Asteroid;
import galacticMail.gameObjects.Moon;
import galacticMail.gameObjects.Rocket;
import general.GamePanel;
import utility.Camera;
import utility.RandomNumberGenerator;
import utility.Vector2;

import java.awt.*;

public class GalacticLevel extends GalacticMailWorld {

  public GalacticLevel(int level) {
    super(level);
  }

  @Override
  protected void initialize() {

    new Camera();

    int numAsteroids = 10 + (int)Math.pow(level, 1.8);
    double asteroidSpeed = 0.95 + (level*0.05);
    for (int i=0; i<numAsteroids; i++) {
      Asteroid asteroid = (Asteroid)instantiate(new Asteroid(
              RandomNumberGenerator.getRandomPosition(
                      0, 0, dimension.width, dimension.height ),
              RandomNumberGenerator.getRandomDouble(0, 360)
      ));
      asteroid.setMoveSpeed(asteroidSpeed);
    }

    int numMoons = 2 + 2*(int)Math.pow(level, 0.6);
    for (int i=0; i<numMoons; i++) {
      instantiate(new Moon(
              RandomNumberGenerator.getRandomPosition(
                      0, 0, dimension.width, dimension.height ),
              RandomNumberGenerator.getRandomDouble(0, 360)
      ));
    }

    instantiate(new Rocket(new Vector2(dimension.width/2, dimension.height/2)));

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Pause);
    GamePanel.getInstance().setEscapeFunction(GamePanel.EscapeFunction.Pause);
  }



  @Override
  public void display(Graphics graphics) {
    graphics.drawImage(getCurrentImage(), 0, 0, null);
    Graphics2D graphics2D = (Graphics2D) graphics;
    Camera.displayPoints(graphics2D, PointsHandler.getInstance().getPoints());
    if(gameState == GameState.Defeat){
      Camera.displayloseScreen(graphics2D);
    }
    if(gameState == GameState.Victory){
      Camera.displayWinScreen(graphics2D);
    }
  }
}
