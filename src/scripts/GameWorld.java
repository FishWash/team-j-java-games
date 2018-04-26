package scripts;

import scripts.gameObjects.Projectile;
import scripts.gameObjects.TriggerGameObject;
import scripts.gameObjects.*;
import scripts.utility.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld implements ClockListener
{
  public enum Player {Neutral, One, Two}
  public enum RenderingLayer {BackgroundGameObject, ForegroundGameObject, Walls, Projectiles, Tanks};
  public final int TILE_SIZE = 32;

  private static GameWorld instance;
  private final Dimension dimension = new Dimension(1024, 1024);

  private CopyOnWriteArrayList<Damageable> damageables              = new CopyOnWriteArrayList<>();
  private HashMap<String, BufferedImage> spriteCache                = new HashMap<>();
  private CollisionHandler collisionHandler                         = new CollisionHandler();

  // These are for rendering
  private CopyOnWriteArrayList<GameObject> backgroundGameObjects    = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> walls                    = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> projectiles              = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> tanks                    = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<GameObject> foregroundGameObjects    = new CopyOnWriteArrayList<>();

  private BufferedImage backgroundImage;
  private Tank p1_tank, p2_tank;
  private PlayerCamera playerOneCamera;
  private PlayerCamera playerTwoCamera;
  private Timer p1_respawnTimer = new Timer();
  private Timer p2_respawnTimer = new Timer();
  private int respawnDelay = 128;
  private boolean p1_respawning, p2_respawning;

  // Initialization
  public GameWorld() {
    instance = this;
    initialize();
  }

  public void initialize() {
    collisionHandler.readMapFile("maps/CollisionTestMap.txt", TILE_SIZE);
    backgroundImage = drawBackgroundImage("maps/CollisionTestMap.txt",
                                          loadSprite("background_tile.png"),
                                          loadSprite("wall_indestructible2.png"));

    spawnTank(Player.One);
    spawnTank(Player.Two);
    instantiate( new HealthPad(new Vector2(32, 32), Player.One) );
    instantiate( new HealthPad(new Vector2(800, 800), Player.Two) );

    Clock.getInstance().addClockObserver(this);
  }

  public static GameWorld getInstance() {
    return instance;
  }

  public void update() {
    if (!p1_tank.getAlive()) {
      if (!p1_respawning) {
        p1_respawnTimer.set(respawnDelay);
        p1_respawning = true;
      }
      else if (p1_respawnTimer.isDone()) {
        spawnTank(Player.One);
        p1_respawning = false;
      }
    }
    if (!p2_tank.getAlive()) {
      if (!p2_respawning) {
        p2_respawnTimer.set(respawnDelay);
        p2_respawning = true;
      }
      else if (p2_respawnTimer.isDone()) {
        spawnTank(Player.Two);
        p2_respawning = false;
      }
    }
  }

  private void spawnTank(Player player) {
    if (player == Player.One) {
      p1_tank = (Tank) instantiate(new Tank(new Vector2(128, 128), Player.One ));
      playerOneCamera = new PlayerCamera(p1_tank);
    }
    else if (player == Player.Two) {
      p2_tank = (Tank) instantiate(new Tank( new Vector2(896, 896), Player.Two ));
      playerTwoCamera = new PlayerCamera(p2_tank);
    }
  }

  // drawBackgroundImage draws background tiles and indestructible walls onto a BufferedImage and returns it.
  private BufferedImage drawBackgroundImage(String mapFileName, BufferedImage backgroundTile,
                                            BufferedImage wallImage) {
    BufferedImage backgroundImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = backgroundImage.createGraphics();

    for(int i=0; i<dimension.width; i += backgroundTile.getWidth()) {
      for (int j=0; j<dimension.height; j += backgroundTile.getHeight()) {
        graphics.drawImage(backgroundTile, i, j, null);
      }
    }

    try {
      Path filePath = Paths.get("src/" + mapFileName);
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

    return backgroundImage;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void getDisplay(Graphics graphics){
    int playerDisplayWidth = (TankGameApplication.windowDimension.width / 2);
    int playerDisplayHeight = TankGameApplication.windowDimension.height;
    BufferedImage currentImage = getCurrentImage();
    BufferedImage p1Display = playerOneCamera.getPlayerDisplay(currentImage, playerDisplayWidth, playerDisplayHeight);
    BufferedImage p2Display = playerTwoCamera.getPlayerDisplay(currentImage, playerDisplayWidth, playerDisplayHeight);
    BufferedImage minimap = Camera.getMinimapDisplay(currentImage);

    graphics.drawImage(p1Display, 0, 0, null);
    graphics.drawImage(p2Display, playerDisplayWidth, 0, null);

    graphics.drawImage(minimap, playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(), null);
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.setStroke(new BasicStroke(4));
    graphics2D.setColor(Color.BLACK);
    graphics2D.drawLine(playerDisplayWidth, 0, playerDisplayWidth,playerDisplayHeight - minimap.getHeight());
    graphics2D.drawRect(playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(), minimap.getWidth(), minimap.getHeight());
  }

  private BufferedImage getCurrentImage() {
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
    for (GameObject go : tanks) {
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
      instance.tanks.add(gameObject);
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
      instance.tanks.remove(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.ForegroundGameObject) {
      instance.foregroundGameObjects.remove(gameObject);
    }
    else if (gameObject.getRenderingLayer() == RenderingLayer.BackgroundGameObject){
      instance.backgroundGameObjects.remove(gameObject);
    }

    if (gameObject instanceof CollidableGameObject) {
      instance.collisionHandler.removeCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.remove((Damageable) gameObject);
    }
    if (gameObject instanceof ClockListener) {
      Clock.getInstance().removeClockObserver((ClockListener) gameObject);
    }
  }

  public static Vector2 getMoveVectorWithCollision(BoxTrigger trigger, Vector2 moveVector) {
    return instance.collisionHandler.getMoveVectorWithCollision(trigger, moveVector);
  }

  public static Collidable findOverlappingCollidable(BoxTrigger trigger) {
    return instance.collisionHandler.findOverlappingCollidable(trigger);
  }

  public static ArrayList<Damageable> findOverlappingEnemyDamageables(BoxTrigger trigger, Player owner) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable d : instance.damageables) {
      if (d instanceof TriggerGameObject) {
        TriggerGameObject tgo = (TriggerGameObject) d;
        if (tgo.isOverlapping(trigger) && ((owner == Player.Neutral) || (tgo.getOwner() != owner))) {
          overlappingDamageables.add(d);
        }
      }
    }
    return overlappingDamageables;
  }
  public static ArrayList<Damageable> findOverlappingFriendlyDamageables(BoxTrigger trigger, Player owner) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable d : instance.damageables) {
      if (d instanceof TriggerGameObject) {
        TriggerGameObject tgo = (TriggerGameObject) d;
        if (tgo.isOverlapping(trigger) && tgo.getOwner() == owner) {
          overlappingDamageables.add(d);
        }
      }
    }
    return overlappingDamageables;
  }

  public static BufferedImage loadSprite(String fileName) {
    BufferedImage _bImg = instance.spriteCache.get(fileName);
    if (_bImg == null) {
      try {
        _bImg = ImageIO.read(instance.getClass().getResourceAsStream("/sprites/" + fileName));
        instance.spriteCache.put(fileName, _bImg);
      } catch (Exception e) {
        System.out.println("ERROR in GameWorld: " + fileName + " not found");
        }
    }
    return _bImg;
  }
}
