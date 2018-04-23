package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld
{
  public enum Player {Neutral, One, Two};

  private static GameWorld instance;

  private Dimension dimension;
  private BufferedImage backgroundImage; //image of GameWorld with background tiles and walls

  private CopyOnWriteArrayList<Wall> walls;
  private CopyOnWriteArrayList<DestructibleWall> destructibleWalls;
  private CopyOnWriteArrayList<Tank> tanks;
  private CopyOnWriteArrayList<Projectile> projectiles;
  // explosions
  private CopyOnWriteArrayList<CollidableGameObject> collidables;
  private CopyOnWriteArrayList<Damageable> damageables;

  private HashMap<String, BufferedImage> spriteCache;

  private Tank p1_tank, p2_tank;

  // Initialization
  public GameWorld(Dimension dimension)
  {
    instance = this;
    this.dimension = dimension;
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

    placeOuterWalls();
    backgroundImage = drawBackgroundImage();

    // Tank Initialization
    p1_tank = (Tank) instantiate(new Tank( new Vector2(100, 100), Player.One ));
    p2_tank = (Tank) instantiate(new Tank( new Vector2(300, 100), Player.Two ));
    p1_tank.setSprite("Tank_blue_basic_strip60.png");
    p2_tank.setSprite("Tank_red_basic_strip60.png");
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
    File file = new File("src/CollisionTestMap.txt");

    try {
      readMap(file.getAbsolutePath());
    } catch(Exception e){
      System.out.println("Error:" + e.getMessage());
    }

    //addWall(1,new Point(0,0));
    for(Wall indestructibleWall : walls){
      indestructibleWall.drawSprite(_graphics);
    }

    return _backgroundImage;
  }

  public synchronized BufferedImage getCurrentImage()
  {
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

  public static GameObject instantiate(GameObject gameObject)
  {
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

    if (gameObject instanceof CollidableGameObject) {
      instance.collidables.add((CollidableGameObject) gameObject);
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

  public static CollidableGameObject findOverlappingGameObject(BoxTrigger trigger) {
    for (CollidableGameObject c : instance.collidables) {
      if (c.isOverlapping(trigger)) {
        return c;
      }
    }

    return null;
  }

  public static Wall findOverlappingWalls(BoxTrigger trigger) {
    for (Wall w : instance.walls) {
      if (w.isOverlapping(trigger)) {
        return w;
      }
    }

    return null;
  }

  public static boolean damageOverlappingEnemyDamageables(BoxTrigger trigger, int damage, Player owner) {
    boolean damageableFound = false;
    for (Damageable d : instance.damageables) {
      if (d instanceof TriggerGameObject && ((TriggerGameObject) d).isOverlapping(trigger)) {
        if (((TriggerGameObject) d).getOwner() != owner) {
          d.takeDamage(damage);
          damageableFound = true;
        }
      }
    }
    return damageableFound;
  }

  public static BufferedImage loadSprite(String fileName)
  {
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

  private void readMap(String file){
    String line;
    BufferedImage wall = loadSprite("wall_indestructible.png");
    int yPos = 0;
    int wallDimensions = wall.getWidth();

    try {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String _line;
      BufferedImage _tileSprite = loadSprite("wall_indestructible.png");
      int _gameWorldWidth = instance.dimension.width;
      int _gameWorldHeight = instance.dimension.height;
      int _xPos, _yPos = 0;

      while ((_line = bufferedReader.readLine()) != null && _yPos < _gameWorldHeight) {
        _xPos = 0;
        for (int i=0; i<_line.length() && _xPos<_gameWorldWidth; i++) {
          char wallChar = _line.charAt(i);
          switch (wallChar) {
            case '1':
              instantiate( new Wall(new Vector2(_xPos, _yPos)) );
              break;
            case '2':
              instantiate( new DestructibleWall(new Vector2(_xPos, _yPos)) );
              break;
          }
          _xPos += _tileSprite.getWidth();
        }

        _yPos += _tileSprite.getHeight();
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  private void placeOuterWalls()
  {
    BufferedImage _tileSprite = loadSprite("wall_indestructible.png");
    int _tileWidth = _tileSprite.getWidth();
    int _tileHeight = _tileSprite.getHeight();
    int _gameWorldWidth = instance.dimension.width;
    int _gameWorldHeight = instance.dimension.height;

    for (int i=0; i<_gameWorldWidth; i+= _tileWidth) {
      instantiate(new Wall(new Vector2(i, 0)));
    }
    for (int i=0; i<_gameWorldWidth; i+= _tileWidth) {
      instantiate(new Wall(new Vector2(0, i)));
    }
    for (int i=0; i<_gameWorldWidth; i+= _tileWidth) {
      instantiate(new Wall(new Vector2(i, _gameWorldHeight-_tileHeight)));
    }
    for (int i=0; i<_gameWorldWidth; i+= _tileWidth) {
      instantiate(new Wall(new Vector2(_gameWorldWidth-_tileWidth, i)));
    }
  }
}
