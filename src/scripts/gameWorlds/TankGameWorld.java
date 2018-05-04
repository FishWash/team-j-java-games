package scripts.gameWorlds;

import scripts.gameObjects.Collidable;
import scripts.gameObjects.Damageable;
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

public abstract class TankGameWorld extends GameWorld
{
  public enum Player {Neutral, One, Two}
  public enum RenderingLayer {BackgroundGameObject, ForegroundGameObject, Walls, Projectiles, Tanks}
  public final int TILE_SIZE = 32;

  protected CopyOnWriteArrayList<Tank> tanks                     = new CopyOnWriteArrayList<>();
  protected CopyOnWriteArrayList<Pickup> pickups                 = new CopyOnWriteArrayList<>();
  protected CopyOnWriteArrayList<Damageable> damageables         = new CopyOnWriteArrayList<>();

  // for rendering
  private CopyOnWriteArrayList<GameObject> walls                 = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> backgroundGameObjects = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> projectiles           = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> players               = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> foregroundGameObjects = new CopyOnWriteArrayList<>();

  protected BufferedImage backgroundImage;

  public static TankGameWorld getInstance() {
    return (TankGameWorld) instance;
  }

  @Override
  protected BufferedImage getCurrentImage() {
    BufferedImage currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics currentImageGraphics = currentImage.createGraphics();

    currentImageGraphics.drawImage(backgroundImage, 0, 0, null);

    for (GameObject go : walls) {
      go.drawSprite(currentImageGraphics);
    }
    for (GameObject go : backgroundGameObjects) {
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
  @Override
  public GameObject instantiate(GameObject gameObject) {
    if (gameObject instanceof Collidable) {
      collisionHandler.addCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().addClockListener((ClockListener) gameObject);
    }
    if (gameObject instanceof Tank) {
      tanks.add((Tank) gameObject);
    }
    if (gameObject instanceof Pickup) {
      pickups.add((Pickup) gameObject);
    }
    if (gameObject instanceof Damageable) {
      damageables.add((Damageable) gameObject);
    }

    if (gameObject instanceof TankGameObject) {
      switch (((TankGameObject) gameObject).getRenderingLayer()) {
        case Walls:
          walls.add(gameObject);
          break;
        case Projectiles:
          projectiles.add(gameObject);
          break;
        case Tanks:
          players.add(gameObject);
          break;
        case ForegroundGameObject:
          foregroundGameObjects.add(gameObject);
          break;
        default:
          backgroundGameObjects.add(gameObject);
      }
    }

    return gameObject;
  }

  // Removes a previously instantiated GameObject from instance lists.
  @Override
  public void destroy(GameObject gameObject) {
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().removeClockListener((ClockListener) gameObject);
    }
    if (gameObject instanceof Collidable) {
      collisionHandler.removeCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      damageables.remove(gameObject);
    }
    if (gameObject instanceof Tank) {
      tanks.remove(gameObject);
    }
    if (gameObject instanceof Pickup) {
      pickups.remove(gameObject);
    }

    if (gameObject instanceof TankGameObject) {
      switch (((TankGameObject) gameObject).getRenderingLayer()) {
        case Walls:
          walls.remove(gameObject);
          break;
        case Projectiles:
          projectiles.remove(gameObject);
          break;
        case Tanks:
          players.remove(gameObject);
          break;
        case ForegroundGameObject:
          foregroundGameObjects.remove(gameObject);
          break;
        default:
          backgroundGameObjects.remove(gameObject);
      }
    }
  }

  public TankGameWorld() {
    instance = this;
    initialize();
  }
  protected abstract void initialize();

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
      System.out.println("ERROR in TankGameWorld: " + e);
    }

    this.backgroundImage = newBackgroundImage;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public Vector2 getMoveVectorWithCollision(BoxTrigger trigger, Vector2 moveVector) {
    return collisionHandler.getMoveVectorWithCollision(trigger, moveVector);
  }

  public CopyOnWriteArrayList<Tank> getTanks() {
    return tanks;
  }

  public CopyOnWriteArrayList<Pickup> getPickups() {
    return pickups;
  }

  public Collidable findOverlappingCollidable(BoxTrigger boxTrigger) {
    return collisionHandler.findOverlappingCollidable(boxTrigger);
  }

  public ArrayList<Damageable> findOverlappingDamageables(Trigger trigger) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable damageable : damageables) {
      if (damageable instanceof TriggerGameObject) {
        TriggerGameObject triggerGameObject = (TriggerGameObject) damageable;
        if (triggerGameObject.getTrigger().isOverlapping(trigger)) {
          overlappingDamageables.add(damageable);
        }
      }
    }
    return overlappingDamageables;
  }
}
