package galacticMail.gameObjects;

import general.GameWorld;
import utility.MultiSprite;
import utility.RandomNumberGenerator;
import utility.Vector2;

import java.awt.image.BufferedImage;

public class Planet extends SpaceObject {

  public Planet(Vector2 position, double rotation) {
    super(position, 24);
    setRotation(rotation);
    renderingLayerIndex = 1;

    BufferedImage spriteStrip = GameWorld.getInstance()
            .loadSprite("Planetoid_lives_strip8.png");
    MultiSprite multiSprite = new MultiSprite(spriteStrip, 8);
    int randomInt = RandomNumberGenerator.getRandomInt(1,
            multiSprite.getNumSubSprites() - 1);
    sprite = multiSprite.getSubSprite(randomInt);
  }

  public void destroy() {
    die();
  }
}
