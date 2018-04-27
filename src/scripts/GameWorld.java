package scripts;

import scripts.gameObjects.*;
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

  protected CollisionHandler collisionHandler                       = new CollisionHandler();
  protected CopyOnWriteArrayList<Damageable> damageables            = new CopyOnWriteArrayList<>();

  // These are for rendering
  private CopyOnWriteArrayList<GameObject> backgroundGameObjects    = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> walls                    = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> projectiles              = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> players                  = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> foregroundGameObjects    = new CopyOnWriteArrayList<>();
  private BufferedImage backgroundImage;

  public GameWorld() {
    instance = this;
    initialize();
  }
  protected abstract void initialize();

//  public void initialize() {
//    collisionHandler.readMapFile("maps/CollisionTestMap.txt", TILE_SIZE);
//    drawBackgroundImage("maps/CollisionTestMap.txt", loadSprite("background_tile.png"),
//                        loadSprite("wall_indestructible2.png"));
//
//    instantiate( new TankSpawner(new Vector2(128, 128), Player.One) );
//    instantiate( new TankSpawner(new Vector2(896, 896), Player.Two) );
//
//    instantiate( new HealthPad(new Vector2(32, 32), Player.One) );
//    instantiate( new HealthPad(new Vector2(800, 800), Player.Two) );
//  }

  public static GameWorld getInstance() {
    return instance;
  }

//  public void spawnTank(Player player) {
//    if (player == Player.One) {
//      p1_tank = (Tank) instantiate(new Tank(new Vector2(128, 128), Player.One ));
//      playerOneCamera = new PlayerCamera(p1_tank);
//    }
//    else if (player == Player.Two) {
//      p2_tank = (Tank) instantiate(new Tank( new Vector2(896, 896), Player.Two ));
//      playerTwoCamera = new PlayerCamera(p2_tank);
//    }
//  }

  // drawBackgroundImage draws background tiles and indestructible walls onto a BufferedImage and returns it.
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
            graphics.drawImage(wallImage, row*TILE_SIZE, column*TILE_SIZE, null);
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
    if (gameObject.getRenderingLayer() == RenderingLayer.Walls) {
      instance.walls.add(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.Projectiles) {
      instance.projectiles.add(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.Tanks) {
      instance.players.add(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.ForegroundGameObject) {
      instance.foregroundGameObjects.add(gameObject);
    }
    else {
      instance.backgroundGameObjects.add(gameObject);
    }

    if (gameObject instanceof Collidable) {
      instance.collisionHandler.addCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.add((Damageable) gameObject);
    }
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().addClockObserver((ClockListener) gameObject);
    }

    return gameObject;
  }
  // Removes a previously instantiated GameObject from instance lists.
  public static void destroy(GameObject gameObject) {
    if (gameObject.getRenderingLayer() == RenderingLayer.Walls) {
      instance.walls.remove(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.Projectiles) {
      instance.projectiles.remove(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.Tanks) {
      instance.players.remove(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.ForegroundGameObject) {
      instance.foregroundGameObjects.remove(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.BackgroundGameObject){
      instance.backgroundGameObjects.remove(gameObject);
    }

    if (gameObject instanceof Collidable) {
      instance.collisionHandler.removeCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.remove((Damageable) gameObject);
    }
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().removeClockObserver((ClockListener) gameObject);
    }
  }

  public Dimension getDimension() {
    return dimension;
  }

  public static Vector2 getMoveVectorWithCollision(BoxTrigger trigger, Vector2 moveVector) {
    return instance.collisionHandler.getMoveVectorWithCollision(trigger, moveVector);
  }

  public CopyOnWriteArrayList<GameObject> getPlayers() {
    return players;
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
