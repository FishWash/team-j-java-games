package galacticMail.gameObjects;

import general.GameWorld;
import utility.AnimatedSprite;
import utility.MultiSprite;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asteroid extends SpaceObject {
  private AnimatedSprite animatedSprite;

  public Asteroid(Vector2 position) {
    super(position, 20);
    moveSpeed = 5;

    GameWorld gameWorld = GameWorld.getInstance();
    BufferedImage spriteStrip = GameWorld.getInstance()
            .loadSprite("Asteroid_strip180.png");
    MultiSprite multiSprite = new MultiSprite(spriteStrip, 180);
    animatedSprite = new AnimatedSprite(multiSprite, 1);
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = animatedSprite.getCurrentSprite();
    super.drawSprite(graphics);
  }
}
