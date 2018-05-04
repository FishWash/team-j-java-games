package galacticMail.gameObjects;

import general.gameObjects.CircleTrigger;
import general.gameObjects.GameObject;
import general.gameObjects.Trigger;
import utility.ClockListener;
import utility.Vector2;

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
    position = Vector2.addVectors(position, moveVector);
  }

  public int getRenderingLayerIndex() {
    return renderingLayerIndex;
  }
}
