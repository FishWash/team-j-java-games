package scripts.gameObjects.spaceObjects;

import scripts.gameWorlds.GameWorld;
import scripts.utility.AnimatedSprite;
import scripts.utility.MultiSprite;
import scripts.utility.Vector2;

import java.awt.*;

public class Asteroid extends SpaceObject {
  private AnimatedSprite animatedSprite;

  public Asteroid(Vector2 position) {
    super(position, 20);
    moveSpeed = 5;

    MultiSprite multiSprite = new MultiSprite(GameWorld.getInstance()
            .loadSprite("Asteroid_strip180.png"), 180);
    animatedSprite = new AnimatedSprite(multiSprite, 1);
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = animatedSprite.getCurrentSprite();
    super.drawSprite(graphics);
  }
}
