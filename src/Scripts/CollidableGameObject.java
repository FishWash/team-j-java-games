package Scripts;

public abstract class CollidableGameObject extends GameObject implements Collidable
{
  protected BoxCollider collider;

  public CollidableGameObject(Vector2 position) {
    super(position);
    collider = new BoxCollider(this);
  }

  public boolean isCollidingWith(BoxCollider otherCollider) {
    return collider.isCollidingWith(otherCollider);
  }
}
