package galacticMail;

import galacticMail.gameObjects.Asteroid;
import utility.Vector2;

public class LevelOne extends GalacticMailWorld {
  @Override
  protected void initialize() {
    instantiate(new Asteroid(new Vector2(64, 64)));
  }
}
