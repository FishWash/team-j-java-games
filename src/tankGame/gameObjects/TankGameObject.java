package tankGame.gameObjects;

import general.gameObjects.GameObject;
import tankGame.TankGameWorld;
import utility.Vector2;

public abstract class TankGameObject extends GameObject {
  protected TankGameWorld.Player owner = TankGameWorld.Player.Neutral;
  protected TankGameWorld.RenderingLayer renderingLayer = TankGameWorld.RenderingLayer.BackgroundGameObject;

  public TankGameObject() {
    super();
  }
  public TankGameObject(Vector2 position) {
    super(position);
  }


  public TankGameWorld.Player getOwner() {
    return owner;
  }
  public TankGameWorld.RenderingLayer getRenderingLayer() {
    return renderingLayer;
  }

  public void setOwner(TankGameWorld.Player owner) {
    this.owner = owner;
  }

}
