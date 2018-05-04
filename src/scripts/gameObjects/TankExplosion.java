package scripts.gameObjects;

import scripts.gameWorlds.GameWorld;
import scripts.gameWorlds.TankGameWorld;
import scripts.utility.*;

import java.awt.*;

public class TankExplosion extends TankGameObject implements ClockListener {

  private AnimatedSprite animatedSprite;

  private Timer lifeTimer = new Timer();
  private int lifeTime = 64;

  public TankExplosion (Vector2 position) {
    super(position);
    MultiSprite multiSprite = new MultiSprite(GameWorld.getInstance().loadSprite(
                      "Tank_explosion_strip19.png"), 19);
    animatedSprite = new AnimatedSprite(multiSprite, 5);
//    this.position = Vector2.subtractVectors(position, new Vector2(0, 48));
    renderingLayer = TankGameWorld.RenderingLayer.ForegroundGameObject;
    TankGameWorld.getInstance().playSound("BIGEXPLOSION.wav");
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
