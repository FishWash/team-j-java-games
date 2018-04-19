package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class GameWorld implements KeyListener
{
  public static GameWorld Instance;

  private Dimension dimension;
  private BufferedImage backgroundImage; //image of GameWorld with background tiles and walls

  private ArrayList<Wall> walls;
  private ArrayList<Tank> tanks;
  private ArrayList<Bullet> bullets;
  // explosions
  private ArrayList<Collidable> collidables;
  private ArrayList<Damageable> damageables;
  private ArrayList<Updatable> updatables;

  private HashMap<String, BufferedImage> spriteMap;

  private Updater gameUpdater;
  private Thread gameUpdaterThread;

  private Tank p1_tank, p2_tank;
  private KeyInputHandler keyInputHandler;

  public GameWorld(Dimension dimension) {
    Instance = this;
    this.dimension = dimension;
    initialize();
  }

  public void initialize() {
    walls = new ArrayList<>();
    tanks = new ArrayList<>();
    bullets = new ArrayList<>();
    collidables = new ArrayList<>();
    damageables = new ArrayList<>();
    updatables = new ArrayList<>();
    spriteMap = new HashMap<>();
    backgroundImage = drawBackgroundImage();

    p1_tank = (Tank) instantiate(new Tank(new Vector2D(100, 100)));
    p2_tank = (Tank) instantiate(new Tank(new Vector2D(300, 100)));

    keyInputHandler = new KeyInputHandler();
    p1_tank.setTankInput(keyInputHandler.getP1_tankInput());
    p2_tank.setTankInput(keyInputHandler.getP2_tankInput());

    gameUpdater = new Updater(updatables);
    gameUpdaterThread = new Thread(gameUpdater);
    gameUpdaterThread.start();
  }

  public void keyPressed(KeyEvent keyEvent) {
    keyInputHandler.readKeyPressed(keyEvent);
  }

  public void keyReleased(KeyEvent keyEvent)
  {
    keyInputHandler.readKeyReleased(keyEvent);
  }

  public void keyTyped(KeyEvent keyEvent)
  {  }

  public synchronized BufferedImage getCurrentImage()
  {
    BufferedImage _currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics _currentImageGraphics = _currentImage.createGraphics();
    _currentImageGraphics.drawImage(backgroundImage, 0, 0, null);

    // draw other stuff. tanks, bullets, destructible walls, etc
    for(Tank _tank : tanks) {
      _tank.drawSprite(_currentImageGraphics);
    }

    return _currentImage;
  }

  private GameObject instantiate(GameObject gameObject) {
    if (gameObject instanceof Wall) {
      walls.add((Wall) gameObject);
    }
    else if (gameObject instanceof Tank) {
      tanks.add((Tank) gameObject);
    }
    else if (gameObject instanceof Bullet) {
      bullets.add((Bullet) gameObject);
    }

    if (gameObject instanceof Collidable) {
      collidables.add((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      damageables.add((Damageable) gameObject);
    }
    if (gameObject instanceof Updatable) {
      updatables.add((Updatable) gameObject);
    }

    return gameObject;
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
    File file = new File("src/EditableMap.txt");

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

  public static BufferedImage loadSprite(String fileName) {
    BufferedImage _bImg = Instance.spriteMap.get(fileName);

    if (_bImg == null) {
      try {
        _bImg = ImageIO.read(Instance.getClass().getResourceAsStream("/Sprites/" + fileName));
        Instance.spriteMap.put(fileName, _bImg);
      }
      catch (Exception e) {
        System.out.println("ERROR: " + fileName + " not found");
      }
    }

    return _bImg;
  }

  private void readMap(String file) throws IOException{
    String line;
    BufferedImage wall = loadSprite("wall_indestructible.png");
    int yPos = 0;
    int wallDimensions = wall.getWidth();
    try {
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while ((line = bufferedReader.readLine()) != null) {
        for (int xPos = 0; xPos < line.length(); xPos ++) {
          char wallNum = line.charAt(xPos);
          addWall(wallNum, new Vector2D(xPos * wallDimensions, yPos * wallDimensions));
        }
        yPos ++;
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
    }

  }

  private void addWall(int wallNum, Vector2D position){
    if(wallNum == '1'){
      instantiate(new Wall(position));
    }
    if(wallNum == '2'){
      instantiate(new DestructibleWall(position));
    }
  }
}
