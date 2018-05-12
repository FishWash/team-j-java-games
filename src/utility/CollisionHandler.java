package utility;

import general.GameWorld;
import general.gameObjects.Collidable;
import tankGame.TankGameWorld;
import general.gameObjects.*;
import tankGame.gameObjects.DestructibleWall;
import tankGame.gameObjects.WallCollidable;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionHandler {
  private static CollisionHandler instance;
  private CopyOnWriteArrayList<Collidable> collidables = new CopyOnWriteArrayList<>();

  public CollisionHandler() {
    instance = this;
  }

  public static CollisionHandler getInstance() {
    return instance;
  }

  // Reads file and creates Collidables for indestructible walls.
  public void readMapFile(String fileName, int tileSize) {
    collidables = new CopyOnWriteArrayList<>();
    try {
      Path filePath = Paths.get("src/tankGame/maps/" + fileName);
      List<String> fileLines = Files.readAllLines(filePath);

      int row = 0;

      // Reads map and creates horizontal WallCollidables for horizontally adjacent indestructible walls.
      for (String line : fileLines) {
        WallCollidable currentWallCollidable = null;
        for (int column = 0; column < line.length(); column++) {
          int tileInt = line.charAt(column) - '0';
          if (tileInt == 1) {
            if (currentWallCollidable == null) {
              currentWallCollidable = (WallCollidable) GameWorld.getInstance().instantiate(
                      new WallCollidable(new Vector2(column * tileSize, row * tileSize)));
              currentWallCollidable.setSize(new Vector2(tileSize, tileSize));
            }
            else {
              currentWallCollidable.addSize(new Vector2(tileSize, 0));
            }
          }
          else {
            currentWallCollidable = null;
            if (tileInt == 2) {
              TankGameWorld.getInstance().instantiate(new DestructibleWall(new Vector2(row * tileSize, column * tileSize)));
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
    for (Collidable collidable : collidables) {
      if ( ((TriggerGameObject)collidable).getTrigger().isOverlapping(trigger) ) {
        return collidable;
      }
    }
    return null;
  }
}
