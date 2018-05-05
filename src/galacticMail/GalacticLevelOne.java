package galacticMail;

import galacticMail.gameObjects.Asteroid;
import galacticMail.gameObjects.Moon;
import galacticMail.gameObjects.Rocket;
import utility.RandomNumberGenerator;
import utility.Vector2;

public class GalacticLevelOne extends GalacticMailWorld {
  @Override
  protected void initialize() {

    for (int i=0; i<8; i++) {
      instantiate(new Asteroid(
              RandomNumberGenerator.getRandomPosition(
                      0, 0, dimension.width, dimension.height ),
              RandomNumberGenerator.getRandomDouble(0, 360)
      ));
    }

    for (int i=0; i<4; i++) {
      instantiate(new Moon(
              RandomNumberGenerator.getRandomPosition(
                      0, 0, dimension.width, dimension.height ),
              RandomNumberGenerator.getRandomDouble(0, 360)
      ));
    }

    instantiate(new Rocket(new Vector2(256, 256)));
  }
}