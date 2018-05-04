package galacticMail.gameObjects;

import general.GameWorld;
import utility.MultiSprite;
import utility.RandomNumberGenerator;
import utility.Vector2;

import java.awt.image.BufferedImage;

public class Moon extends SpaceObject {
  public Moon(Vector2 position, double rotation) {
    super(position, 32);
    setRotation(rotation);
    moveSpeed = 0.8;
    renderingLayerIndex = 1;

    BufferedImage spriteStrip = GameWorld.getInstance()
            .loadSprite("Bases_strip8.png");
    MultiSprite multiSprite = new MultiSprite(spriteStrip, 8);
    sprite = multiSprite.getSubSprite(RandomNumberGenerator.getRandomInt(0, 7));
  }
}
