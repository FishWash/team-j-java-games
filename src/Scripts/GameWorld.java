package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    p1_tank = (Tank) instantiate(new Tank( new Vector2(96, 96), Player.One ));
    p2_tank = (Tank) instantiate(new Tank( new Vector2(864, 864), Player.Two ));

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
      mapGrid = new int[yMax][xMax];

      for (int i=0; i<yMax && line!=null; i++) {
        for (int j=0; j<xMax && j<line.length(); j++) {
          int tileInt = line.charAt(j) - '0';
          if (tileInt >= 0 && tileInt <= 2) {
            mapGrid[i][j] = tileInt;
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

    initializeWalls();
    initializeWallCollidables();
  }

  private void initializeWalls() {
    for (int i=0; i<mapGrid.length; i++) {
      for (int j=0; j<mapGrid[i].length; j++) {
        if (mapGrid[i][j] == 1) {
          instantiate(new Wall(new Vector2(j*TILE_SIZE, i*TILE_SIZE)));
        }
        else if (mapGrid[i][j] == 2) {
          instantiate(new DestructibleWall(new Vector2(j*TILE_SIZE, i*TILE_SIZE)));
        }
      }
    }
  }

  private void initializeWallCollidables() {
    for (int i=0; i<mapGrid.length; i++) {
      for (int j=0; j<mapGrid[i].length; j++) {
        if (mapGrid[i][j] == 1) {
          CollidableGameObject collidableGameObject = new CollidableGameObject(new Vector2(j*TILE_SIZE, i*TILE_SIZE));
          int collidableLength = 0;
          do {
            collidableLength++;
            j++;
          } while (j<mapGrid[i].length && mapGrid[i][j] == 1);
          collidableGameObject.setTriggerSize(new Vector2(collidableLength*TILE_SIZE, TILE_SIZE));
          collidables.add(collidableGameObject);
        }
      }
    }
  }

  public static GameWorld getInstance() {
    return instance;
  }

  private BufferedImage drawBackgroundImage() {
    BufferedImage bgTile = loadSprite("background_tile.png");
    BufferedImage backgroundImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = backgroundImage.createGraphics();

    for(int i=0; i<dimension.width; i += bgTile.getWidth()) {
      for (int j=0; j<dimension.height; j += bgTile.getHeight()) {
        graphics.drawImage(bgTile, i, j, null);
      }
    }

    for (Wall w : walls) {
      w.drawSprite(graphics);
    }

    return backgroundImage;
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

  public void getDisplay(Graphics graphics){
    int playerDisplayWidth = (TankGameApplication.windowDimension.width / 2);
    int playerDisplayHeight = TankGameApplication.windowDimension.height;
    BufferedImage currentImage = getCurrentImage();
    BufferedImage p1Display = Camera.getPlayerDisplay(currentImage, tanks.get(0), playerDisplayWidth, playerDisplayHeight);
    BufferedImage p2Display = Camera.getPlayerDisplay(currentImage, tanks.get(1), playerDisplayWidth, playerDisplayHeight);
    BufferedImage minimap = Camera.getMinimapDisplay(currentImage);

    graphics.drawImage(p1Display, 0, 0, null);
    graphics.drawImage(p2Display, playerDisplayWidth, 0, null);

    graphics.drawImage(minimap, playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(), null);
    Graphics2D g2D = (Graphics2D) graphics;
    g2D.setStroke(new BasicStroke(4));
    g2D.setColor(Color.BLACK);
    g2D.drawLine(playerDisplayWidth, 0, playerDisplayWidth,playerDisplayHeight - minimap.getHeight());
    g2D.drawRect(playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(), minimap.getWidth(), minimap.getHeight());
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

  public static Collidable findOverlappingCollidable(BoxTrigger trigger) {
    for (Collidable c : instance.collidables) {
      if (((TriggerGameObject) c).isOverlapping(trigger)) {
        return c;
      }
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
        System.out.println("ERROR in GameWorld.loadSprite(): " + fileName + " not found");
        }
    }
    return _bImg;
  }
}
