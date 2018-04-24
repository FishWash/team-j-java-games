package Scripts;

public interface Collidable
{

  Vector2 getMoveVectorWithCollision(BoxTrigger otherTrigger, Vector2 moveVector);
}
