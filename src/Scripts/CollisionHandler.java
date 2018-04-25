package Scripts;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionHandler {
  private CopyOnWriteArrayList<Collidable> collidables = new CopyOnWriteArrayList<>();

  // Reads file and creates Collidables for indestructible walls.
  public void readMapFile(String fileName, int tileSize) {
    try {
      Path filePath = Paths.get("src/" + fileName);
      List<String> fileLines = Files.readAllLines(filePath);

      int row = 0;

      for (String line : fileLines) {
        CollidableGameObject currentCollidable = null;
        for (int column = 0; column < line.length(); column++) {
          int tileInt = line.charAt(column) - '0';
          if (tileInt == 1) {
            if (currentCollidable == null) {
              currentCollidable = (CollidableGameObject) GameWorld.instantiate(
                      new CollidableGameObject(new Vector2(column * tileSize, row * tileSize)));
              currentCollidable.setTriggerSize(new Vector2(tileSize, tileSize));
            }
            else {
              currentCollidable.addTriggerSize(new Vector2(tileSize, 0));
            }
          }
          else {
            currentCollidable = null;
            if (tileInt == 2) {
              GameWorld.instantiate(new DestructibleWall(new Vector2(row * tileSize, column * tileSize)));
            }
          }
        }

        row++;
      }

    } catch (Exception e) {
      System.out.println("ERROR in CollisionHandler: " + e);
    }
  }

  public void addCollidable(Collidable collidable) {
    collidables.add(collidable);
  }
  public void removeCollidable(Collidable collidable) {
    collidables.remove(collidable);
  }

  public Vector2 getMoveVectorWithCollision(BoxTrigger trigger, Vector2 moveVector) {
    for (Collidable c : collidables) {
      moveVector = c.getMoveVectorWithCollision(trigger, moveVector);
    }
    return moveVector;
  }

  public Collidable findOverlappingCollidable(BoxTrigger trigger) {
    for (Collidable c : collidables) {
      if (((TriggerGameObject) c).isOverlapping(trigger)) {
        return c;
      }
    }
    return null;
  }
}