package galacticMail.gameObjects;

import galacticMail.GalacticMailWorld;
import general.GameWorld;
import utility.MultiSprite;
import utility.RandomNumberGenerator;
import utility.SpriteHandler;
import utility.Vector2;

import java.awt.image.BufferedImage;

public class Planet extends SpaceObject {

  public Planet(Vector2 position, double rotation) {
    super(position, 24);
    setRotation(rotation);
    renderingLayerIndex = 1;
    setRandomSprite();
  }

  private void setRandomSprite() {
    BufferedImage spriteStrip = SpriteHandler.getInstance()
            .loadSprite("Planetoid_lives_strip8.png");
    MultiSprite multiSprite = new MultiSprite(spriteStrip, 8);
    int randomInt = RandomNumberGenerator.getRandomInt(1,
                                                       multiSprite.getNumSubSprites() - 1);
    sprite = multiSprite.getSubSprite(randomInt);
  }

  @Override
  public void update() {
    super.update();
    if (alive && ((GalacticMailWorld)GameWorld.getInstance()).isGameOver()) {
      die();
    }
  }

  public void destroy() {
    die();
  }
}
