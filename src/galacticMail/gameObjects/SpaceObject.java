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
    Vector2 newPosition = Vector2.addVectors(position, moveVector);
    setPosition(newPosition);
  }

  public Trigger getTrigger() {
    return trigger;
  }

  public int getRenderingLayerIndex() {
    return renderingLayerIndex;
  }

  public void setPosition(Vector2 newPosition) {
    Dimension dimension = GameWorld.getInstance().getDimension();
    // for wrapping GameObjects around the map
    double xPos = (dimension.width + newPosition.x) % dimension.width;
    double yPos = (dimension.height + newPosition.y) % dimension.height;
    position = new Vector2(xPos, yPos);
  }

  public void setMoveSpeed(double moveSpeed) {
    this.moveSpeed = moveSpeed;
  }
}
