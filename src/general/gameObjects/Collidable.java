package general.gameObjects;

import utility.Vector2;

public interface Collidable
{

  Vector2 getMoveVectorWithCollision(BoxTrigger otherTrigger, Vector2 moveVector);
}
