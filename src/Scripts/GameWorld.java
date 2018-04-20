package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld implements KeyListener
{
  public static GameWorld Instance;
  public int framesSinceStart;

  private Dimension dimension;
  private BufferedImage backgroundImage; //image of GameWorld with background tiles and walls

  private CopyOnWriteArrayList<Wall> walls;
  private CopyOnWriteArrayList<DestructibleWall> destructibleWalls;
  private CopyOnWriteArrayList<Tank> tanks;
  private CopyOnWriteArrayList<Bullet> bullets;
  // explosions
  private CopyOnWriteArrayList<Collidable> collidables;
  private CopyOnWriteArrayList<Damageable> damageables;
  private CopyOnWriteArrayList<Updatable> updatables;

  private HashMap<String, BufferedImage> spriteCache;

  private Tank p1_tank, p2_tank;
  private KeyInputHandler keyInputHandler;

  // Initialization
  public GameWorld(Dimension dimension)
  {
    Instance = this;
    this.dimension = dimension;
    initialize();
  }

  public void initialize()
  {
    walls = new CopyOnWriteArrayList<>();
    destructibleWalls = new CopyOnWriteArrayList<>();
    tanks = new CopyOnWriteArrayList<>();
    bullets = new CopyOnWriteArrayList<>();
    collidables = new CopyOnWriteArrayList<>();
    damageables = new CopyOnWriteArrayList<>();
    updatables = new CopyOnWriteArrayList<>();
    spriteCache = new HashMap<>();

    placeOuterWalls();
    backgroundImage = drawBackgroundImage();

    // Tank Initialization
    p1_tank = (Tank) instantiate( new Tank(new Vector2(100, 100)) );
    p2_tank = (Tank) instantiate( new Tank(new Vector2(300, 100)) );

    keyInputHandler = new KeyInputHandler();
    p1_tank.setTankInput(keyInputHandler.getP1_tankInput());
    p2_tank.setTankInput(keyInputHandler.getP2_tankInput());

    p1_tank.setSprite("Tank_blue_basic_strip60.png");
    p2_tank.setSprite("Tank_red_basic_strip60.png");
  }

  private BufferedImage drawBackgroundImage()
  {
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

  // Called from GamePanel
  public void update()
  {
    for (Updatable _u : updatables) {
      _u.update();
    }
    framesSinceStart++;
  }

  public synchronized BufferedImage getCurrentImage()
  {
    BufferedImage _currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics _currentImageGraphics = _currentImage.createGraphics();
    _currentImageGraphics.drawImage(backgroundImage, 0, 0, null);

    // draw other stuff. tanks, bullets, destructible walls, etc
    for (DestructibleWall _dWall : destructibleWalls) {
      _dWall.drawSprite(_currentImageGraphics);
    }

    for (Tank _tank : tanks) {
      _tank.drawSprite(_currentImageGraphics);
    }

    for (Bullet _bullet : bullets) {
      _bullet.drawSprite(_currentImageGraphics);
    }

    return _currentImage;
  }

  // Keyboard Input
  public void keyPressed(KeyEvent keyEvent)
  {
    keyInputHandler.readKeyPressed(keyEvent);
  }

  public void keyReleased(KeyEvent keyEvent)
  {
    keyInputHandler.readKeyReleased(keyEvent);
  }

  public void keyTyped(KeyEvent keyEvent) {}

  // instantiate() takes a GameObject and puts it in the appropriate ArrayLists.
  public static GameObject instantiate(GameObject gameObject)
  {
    if (gameObject instanceof Wall) {
      Instance.walls.add((Wall) gameObject);
    }
    else if (gameObject instanceof DestructibleWall) {
      Instance.destructibleWalls.add((DestructibleWall) gameObject);
    }
    else if (gameObject instanceof Bullet) {
      Instance.bullets.add((Bullet) gameObject);
    }
    else if (gameObject instanceof Tank) {
      Instance.tanks.add((Tank) gameObject);
    }
    else {
      return null;
    }

    if (gameObject instanceof Collidable) {
      Instance.collidables.add((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      Instance.damageables.add((Damageable) gameObject);
    }
    if (gameObject instanceof Updatable) {
      Instance.updatables.add((Updatable) gameObject);
    }

    return gameObject;
  }

  public static boolean checkCollisions(BoxCollider collider)
  {
    for (Collidable _c : Instance.collidables) {
      if (_c.isCollidingWith(collider)) {
        System.out.println("Colliding");
        return true;
      }
    }

    return false;
  }

  public static BufferedImage loadSprite(String fileName)
  {
    BufferedImage _bImg = Instance.spriteCache.get(fileName);
    if (_bImg == null) {
      try {
        _bImg = ImageIO.read(Instance.getClass().getResourceAsStream("/Sprites/" + fileName));
        Instance.spriteCache.put(fileName, _bImg);
      }
      catch (Exception e) {
        System.out.println("ERROR: " + fileName + " not found");
      }
    }

    return _bImg;
  }

  private void readMap(String file) throws IOException
  {
    try {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String _line;
      BufferedImage _tileSprite = loadSprite("wall_indestructible.png");
      int _gameWorldWidth = Instance.dimension.width;
      int _gameWorldHeight = Instance.dimension.height;
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
    int _gameWorldWidth = Instance.dimension.width;
    int _gameWorldHeight = Instance.dimension.height;

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
