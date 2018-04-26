package scripts.gameObjects;

import scripts.utility.Vector2;

public interface Collidable
{

  Vector2 getMoveVectorWithCollision(BoxTrigger otherTrigger, Vector2 moveVector);
}
