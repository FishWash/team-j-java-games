package galacticMail.gameObjects;

import utility.AnimatedSprite;
import utility.MultiSprite;
import utility.SpriteHandler;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends SpaceObject {
  private AnimatedSprite animatedSprite;

  public Explosion(Vector2 position, double rotation) {
    super(position, 0);
    setRotation(rotation);
    moveSpeed = 1;
    BufferedImage spriteStrip = SpriteHandler.getInstance()
            .loadSprite("Explosion_space_strip9.png");
    MultiSprite multiSprite = new MultiSprite(spriteStrip, 9);
    animatedSprite = new AnimatedSprite(multiSprite, 5);
    renderingLayerIndex = 4;
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = animatedSprite.getCurrentSprite();
    super.drawSprite(graphics);
  }
}
