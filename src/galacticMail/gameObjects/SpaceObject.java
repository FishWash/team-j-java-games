package galacticMail.gameObjects;

import general.GameWorld;
import general.gameObjects.CircleTrigger;
import general.gameObjects.GameObject;
import general.gameObjects.Trigger;
import utility.ClockListener;
import utility.Vector2;

import java.awt.*;

public abstract class SpaceObject extends GameObject implements ClockListener {

  protected Trigger trigger;
  protected double moveSpeed = 0;
  protected int renderingLayerIndex = 0;

  public SpaceObject(Vector2 position, double radius) {
    super(position);
    trigger = new CircleTrigger(this, radius);
  }

  public void update() {
    move();
  }

  private void move() {
    Vector2 moveVector = Vector2.newRotationMagnitudeVector(rotation,
                                                            moveSpeed);
    Dimension dimension = GameWorld.getInstance().getDimension();
    // for wrapping GameObjects around the map
    double xPos = (dimension.width + position.x + moveVector.x) % dimension.width;
    double yPos = (dimension.height + position.y + moveVector.y) % dimension.height;
    position = new Vector2(xPos, yPos);
  }

  public int getRenderingLayerIndex() {
    return renderingLayerIndex;
  }
}
