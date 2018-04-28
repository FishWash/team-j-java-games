package scripts.gameWorlds;

import scripts.Collidable;
import scripts.Damageable;
import scripts.DisplayableElement;
import scripts.gameObjects.*;
import scripts.gameObjects.pickups.Pickup;
import scripts.utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GameWorld extends DisplayableElement
{
  public enum Player {Neutral, One, Two}
  public enum RenderingLayer {BackgroundGameObject, ForegroundGameObject, Walls, Projectiles, Tanks}
  public final int TILE_SIZE = 32;

  protected static GameWorld instance;
  protected Dimension dimension;

  protected CollisionHandler collisionHandler                    = new CollisionHandler();
  protected CopyOnWriteArrayList<Tank> tanks                     = new CopyOnWriteArrayList<>();
  protected CopyOnWriteArrayList<Pickup> pickups                 = new CopyOnWriteArrayList<>();
  protected CopyOnWriteArrayList<Damageable> damageables         = new CopyOnWriteArrayList<>();

  // for rendering
  private CopyOnWriteArrayList<GameObject> backgroundGameObjects = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> walls                 = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> projectiles           = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> players               = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> foregroundGameObjects = new CopyOnWriteArrayList<>();
  private BufferedImage backgroundImage;

  public GameWorld() {
    instance = this;
    initialize();
  }
  protected abstract void initialize();

  public static GameWorld getInstance() {
    return instance;
  }

  // drawBackgroundImage draws background tiles and indestructible walls onto backgroundImage.
  protected void drawBackgroundImage(String mapFileName, BufferedImage backgroundTile,
                                            BufferedImage wallImage) {
    BufferedImage newBackgroundImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = newBackgroundImage.createGraphics();

    for(int i=0; i<dimension.width; i += backgroundTile.getWidth()) {
      for (int j=0; j<dimension.height; j += backgroundTile.getHeight()) {
        graphics.drawImage(backgroundTile, i, j, null);
      }
    }

    try {
      Path filePath = Paths.get("src/maps/" + mapFileName);
      List<String> fileLines = Files.readAllLines(filePath);

      int row = 0;
      for (String line : fileLines) {
        for (int column = 0; column < line.length(); column++) {
          int tileInt = line.charAt(column) - '0';
          if (tileInt == 1) {
            graphics.drawImage(wallImage, column*TILE_SIZE, row*TILE_SIZE, null);
          }
        }
        row++;
      }
    } catch (Exception e) {
      System.out.println("ERROR in GameWorld: " + e);
    }

    this.backgroundImage = newBackgroundImage;
  }

  protected BufferedImage getCurrentImage() {
    BufferedImage currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics currentImageGraphics = currentImage.createGraphics();

    currentImageGraphics.drawImage(backgroundImage, 0, 0, null);

    for (GameObject go : backgroundGameObjects) {
      go.drawSprite(currentImageGraphics);
    }
    for (GameObject go : walls) {
      go.drawSprite(currentImageGraphics);
    }
    for (GameObject go : projectiles) {
      go.drawSprite(currentImageGraphics);
    }
    for (GameObject go : players) {
      go.drawSprite(currentImageGraphics);
    }
    for (GameObject go : foregroundGameObjects) {
      go.drawSprite(currentImageGraphics);
    }

    return currentImage;
  }

  // Takes a GameObject and adds it to the correct instance lists.
  public static GameObject instantiate(GameObject gameObject) {
    if (gameObject instanceof Collidable) {
      instance.collisionHandler.addCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().addClockListener((ClockListener) gameObject);
    }
    if (gameObject instanceof Tank) {
      instance.tanks.add((Tank) gameObject);
    }
    if (gameObject instanceof Pickup) {
      instance.pickups.add((Pickup) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.add((Damageable) gameObject);
    }

    switch(gameObject.getRenderingLayer()) {
      case Walls:
        instance.walls.add(gameObject);
        break;
      case Projectiles:
        instance.projectiles.add(gameObject);
        break;
      case Tanks:
        instance.players.add(gameObject);
        break;
      case ForegroundGameObject:
        instance.foregroundGameObjects.add(gameObject);
        break;
      default:
        instance.backgroundGameObjects.add(gameObject);
    }

    return gameObject;
  }
  // Removes a previously instantiated GameObject from instance lists.
  public static void destroy(GameObject gameObject) {
    if (gameObject instanceof Collidable) {
      instance.collisionHandler.removeCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().removeClockListener((ClockListener) gameObject);
    }
    if (gameObject instanceof Tank) {
      instance.tanks.remove((Tank) gameObject);
    }
    if (gameObject instanceof Pickup) {
      instance.pickups.remove((Pickup) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.remove((Damageable) gameObject);
    }

    switch(gameObject.getRenderingLayer()) {
      case Walls:
        instance.walls.remove(gameObject);
        break;
      case Projectiles:
        instance.projectiles.remove(gameObject);
        break;
      case Tanks:
        instance.players.remove(gameObject);
        break;
      case ForegroundGameObject:
        instance.foregroundGameObjects.remove(gameObject);
        break;
      default:
        instance.backgroundGameObjects.remove(gameObject);
    }
  }

  public Dimension getDimension() {
    return dimension;
  }

  public static Vector2 getMoveVectorWithCollision(BoxTrigger trigger, Vector2 moveVector) {
    return instance.collisionHandler.getMoveVectorWithCollision(trigger, moveVector);
  }

  public CopyOnWriteArrayList<Tank> getTanks() {
    return tanks;
  }

  public CopyOnWriteArrayList<Pickup> getPickups() {
    return pickups;
  }

  public static Collidable findOverlappingCollidable(BoxTrigger boxTrigger) {
    return instance.collisionHandler.findOverlappingCollidable(boxTrigger);
  }

  public static ArrayList<Damageable> findOverlappingDamageables(BoxTrigger boxTrigger) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable d : instance.damageables) {
      if (d instanceof BoxTriggerGameObject) {
        BoxTriggerGameObject btgo = (BoxTriggerGameObject) d;
        if (btgo.isOverlapping(boxTrigger)) {
          overlappingDamageables.add(d);
        }
      }
    }
    return overlappingDamageables;
  }
  public static ArrayList<Damageable> findOverlappingEnemyDamageables(BoxTrigger boxTrigger, Player owner) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable d : instance.damageables) {
      if (d instanceof BoxTriggerGameObject) {
        BoxTriggerGameObject tgo = (BoxTriggerGameObject) d;
        if (tgo.isOverlapping(boxTrigger) && ((owner == Player.Neutral) || (tgo.getOwner() != owner))) {
          overlappingDamageables.add(d);
        }
      }
    }
    return overlappingDamageables;
  }
  public static ArrayList<Damageable> findOverlappingFriendlyDamageables(BoxTrigger boxTrigger, Player owner) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable d : instance.damageables) {
      if (d instanceof BoxTriggerGameObject) {
        BoxTriggerGameObject tgo = (BoxTriggerGameObject) d;
        if (tgo.isOverlapping(boxTrigger) && tgo.getOwner() == owner) {
          overlappingDamageables.add(d);
        }
      }
    }
    return overlappingDamageables;
  }
}
