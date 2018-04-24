package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld
{
  public enum Player {Neutral, One, Two}
  public final int TILE_SIZE = 32;

  private static GameWorld instance;

  private Dimension dimension = new Dimension(1024, 1024);
  private BufferedImage backgroundImage; //image of GameWorld with background tiles and walls

  private CopyOnWriteArrayList<Wall> walls;
  private CopyOnWriteArrayList<DestructibleWall> destructibleWalls;
  private CopyOnWriteArrayList<Tank> tanks;
  private CopyOnWriteArrayList<Projectile> projectiles;
  // explosions
  private CopyOnWriteArrayList<Collidable> collidables;
  private CopyOnWriteArrayList<Damageable> damageables;

  private HashMap<String, BufferedImage> spriteCache;

  private int[][] mapGrid;

  private Tank p1_tank, p2_tank;

  // Initialization
  public GameWorld()
  {
    instance = this;
    initialize();
  }

  public void initialize()
  {
    walls = new CopyOnWriteArrayList<>();
    destructibleWalls = new CopyOnWriteArrayList<>();
    tanks = new CopyOnWriteArrayList<>();
    projectiles = new CopyOnWriteArrayList<>();
    collidables = new CopyOnWriteArrayList<>();
    damageables = new CopyOnWriteArrayList<>();
    spriteCache = new HashMap<>();

    readMap("src/CollisionTestMap.txt");

    backgroundImage = drawBackgroundImage();

    // Tank Initialization
    p1_tank = (Tank) instantiate(new Tank( new Vector2(100, 100), Player.One ));
    p2_tank = (Tank) instantiate(new Tank( new Vector2(300, 100), Player.Two ));
    p1_tank.setSprite("Tank_blue_basic_strip60.png");
    p2_tank.setSprite("Tank_red_basic_strip60.png");
  }

  private void readMap(String fileName){
    try {
      File file = new File(fileName);
      fileName = file.getAbsolutePath();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      int xMax = dimension.width/TILE_SIZE;
      int yMax = dimension.height/TILE_SIZE;
      mapGrid = new int[xMax][yMax];

      for (int y=0; y < yMax && line != null; y++) {
        for (int x=0; x < xMax && x < line.length(); x++) {
          int tileInt = line.charAt(x) - '0';
          if (tileInt >= 0 && tileInt <= 2) {
            mapGrid[x][y] = tileInt;
          }
        }
        line = bufferedReader.readLine();
      }

    } catch(Exception e){
      System.out.println(e.getMessage());
    }

    // Add outer walls
    for (int i=0; i<mapGrid.length; i++) {
      mapGrid[i][0] = 1;
      mapGrid[i][mapGrid[i].length-1] = 1;
    }
    for (int j=0; j<mapGrid[0].length; j++) {
      mapGrid[0][j] = 1;
      mapGrid[mapGrid.length-1][j] = 1;
    }

//    for (int i=0; i<mapGrid.length; i++) {
//      for (int j=0; j<mapGrid[i].length; j++) {
//        System.out.print(mapGrid[i][j] + " ");
//      }
//      System.out.println();
//    }
  }

  public static GameWorld getInstance() {
    return instance;
  }

  private BufferedImage drawBackgroundImage() {
    BufferedImage _bgTile = loadSprite("background_tile.png");
    BufferedImage _backgroundImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics _graphics = _backgroundImage.createGraphics();

    for(int i=0; i<dimension.width; i += _bgTile.getWidth()) {
      for (int j=0; j<dimension.height; j += _bgTile.getHeight()) {
        _graphics.drawImage(_bgTile, i, j, null);
      }
    }

    for(Wall wall : walls){
      wall.drawSprite(_graphics);
    }

    return _backgroundImage;
  }

  public synchronized BufferedImage getCurrentImage() {
    BufferedImage _currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics _currentImageGraphics = _currentImage.createGraphics();
    _currentImageGraphics.drawImage(backgroundImage, 0, 0, null);

    for (DestructibleWall dw : destructibleWalls) {
      dw.drawSprite(_currentImageGraphics);
    }
    for (Projectile p : projectiles) {
      p.drawSprite(_currentImageGraphics);
    }
    for (Tank t : tanks) {
      t.drawSprite(_currentImageGraphics);
    }

    return _currentImage;
  }

  public BufferedImage getPlayerDisplay(BufferedImage currentImage, GameObject gameObject){
    int gameWidth = currentImage.getWidth();
    int playerDisplayWidth = (TankGameApplication.windowDimension.width / 2);
    int gameHeight = currentImage.getHeight();
    int playerDisplayHeight = TankGameApplication.windowDimension.height;
    Vector2 tankPosition = gameObject.getCenterPosition();

    int displayX = (int) Math.max(0, Math.min(gameWidth - playerDisplayWidth, tankPosition.x - playerDisplayWidth / 2));
    int displayY = (int) Math.max(0, Math.min(gameHeight - playerDisplayHeight, tankPosition.y - playerDisplayHeight / 2));

    BufferedImage playerOneView = currentImage.getSubimage(displayX, displayY, playerDisplayWidth, playerDisplayHeight);

    return playerOneView;
  }

  public synchronized void gameDisplay(Graphics graphics){
    BufferedImage currentImage = getCurrentImage();

    int playerDisplayWidth = (TankGameApplication.windowDimension.width / 2);
    int playerDisplayHeight = TankGameApplication.windowDimension.height;
    BufferedImage playerOneView = getPlayerDisplay(currentImage, tanks.get(0));
    BufferedImage playerTwoView = getPlayerDisplay(currentImage, tanks.get(1));
    graphics.drawImage(playerOneView, 0, 0, null);
    graphics.drawImage(playerTwoView, playerDisplayWidth, 0, null);

    BufferedImage minimap = minimapDisplay(currentImage);
    graphics.drawImage(minimap, playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(), null);
    graphics.drawLine(playerDisplayWidth, 0, playerDisplayWidth,playerDisplayHeight - minimap.getHeight());
    graphics.drawRect(playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(), minimap.getWidth(), minimap.getHeight());
  }

  public BufferedImage minimapDisplay(BufferedImage currentImage){
    int minimapSize = TankGameApplication.gameDimension.width / 5;
    BufferedImage resizedMap = new BufferedImage(minimapSize, minimapSize, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    at.scale(0.2, 0.2);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    resizedMap = scaleOp.filter(currentImage, resizedMap);

    return resizedMap;
  }

  public static GameObject instantiate(GameObject gameObject) {
    if (gameObject instanceof DestructibleWall) {
      instance.destructibleWalls.add((DestructibleWall) gameObject);
    }
    else if (gameObject instanceof Wall) {
      instance.walls.add((Wall) gameObject);
    }
    else if (gameObject instanceof Projectile) {
      instance.projectiles.add((Projectile) gameObject);
    }
    else if (gameObject instanceof Tank) {
      instance.tanks.add((Tank) gameObject);
    }
    else {
      return null;
    }

    if (gameObject instanceof Collidable) {
      instance.collidables.add((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.add((Damageable) gameObject);
    }
    if (gameObject instanceof ClockObserver) {
      Clock.getInstance().addClockObserver((ClockObserver) gameObject);
    }

    return gameObject;
  }

  public static void destroy(GameObject gameObject) {
    if (gameObject instanceof DestructibleWall) {
      instance.destructibleWalls.remove(gameObject);
    }
    else if (gameObject instanceof Wall) {
      instance.walls.remove(gameObject);
    }
    else if (gameObject instanceof Projectile) {
      instance.projectiles.remove(gameObject);
    }
    else if (gameObject instanceof Tank) {
      instance.tanks.remove(gameObject);
    }

    if (gameObject instanceof CollidableGameObject) {
      instance.collidables.remove(gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.remove(gameObject);
    }
    if (gameObject instanceof ClockObserver) {
      Clock.getInstance().removeClockObserver((ClockObserver) gameObject);
    }
  }

  public static void moveWithCollision(TriggerGameObject triggerGameObject, Vector2 moveVector) {
    for (Collidable c : instance.collidables) {
      moveVector = c.getMoveVectorWithCollision(triggerGameObject.getTrigger(), moveVector);
    }
    triggerGameObject.movePosition(moveVector);
  }

  public static Wall findOverlappingWall(BoxTrigger trigger) {
    for (Wall w : instance.walls) {
    }
    return null;
  }

  public static ArrayList<Damageable> findOverlappingEnemyDamageable(BoxTrigger trigger, Player owner) {
    ArrayList<Damageable> overlappingDamageables = new ArrayList<>();
    for (Damageable d : instance.damageables) {
      if (d instanceof TriggerGameObject) {
        TriggerGameObject tgo = (TriggerGameObject) d;
        if (tgo.isOverlapping(trigger) && (tgo.getOwner() != owner)) {
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
        _bImg = ImageIO.read(instance.getClass().getResourceAsStream("/Sprites/" + fileName));
        instance.spriteCache.put(fileName, _bImg);
      } catch (Exception e) {
        System.out.println("ERROR: " + fileName + " not found");
        }
    }
    return _bImg;
  }
}
