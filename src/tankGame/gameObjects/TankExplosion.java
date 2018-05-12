package tankGame.gameObjects;

import tankGame.TankGameWorld;
import utility.*;

import java.awt.*;

public class TankExplosion extends TankGameObject implements ClockListener {

  private AnimatedSprite animatedSprite;

  private Timer lifeTimer = new Timer(128);

  public TankExplosion (Vector2 position) {
    super(Vector2.addVectors(position, new Vector2(0, -32)));
    MultiSprite multiSprite = new MultiSprite(SpriteHandler.getInstance().loadSprite(
                      "Tank_explosion_strip19.png"), 19);
    animatedSprite = new AnimatedSprite(multiSprite, 5);
    renderingLayer = TankGameWorld.RenderingLayer.ForegroundGameObject;
    SoundHandler.getInstance().playSound("BIGEXPLOSION.wav");
  }

  public void update() {
    if (lifeTimer.isDone()) {
      die();
    }
  }

  @Override
  public void drawSprite(Graphics graphics) {
    sprite = animatedSprite.getCurrentSprite();
    super.drawSprite(graphics);
  }
}
