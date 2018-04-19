package Scripts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
  // observers

  private HashMap<String, BufferedImage> spriteMap;

  private Tank p1_tank, p2_tank;
  private KeyInputHandler keyInputHandler;

  public GameWorld(Dimension dimension)
  {
    Instance = this;
    this.dimension = dimension;
    initialize();
  }

  public void initialize()
  {
    walls = new ArrayList<>();
    tanks = new ArrayList<>();
    bullets = new ArrayList<>();
    collidables = new ArrayList<>();
    damageables = new ArrayList<>();
    spriteMap = new HashMap<>();

    backgroundImage = drawBackgroundImage();
    p1_tank = (Tank) instantiate(new Tank(new Point(100, 100)));
    p2_tank = (Tank) instantiate(new Tank(new Point(300, 100)));

    keyInputHandler = new KeyInputHandler();
    p1_tank.setTankInput(keyInputHandler.getP1_tankInput());
    p2_tank.setTankInput(keyInputHandler.getP2_tankInput());
  }

  public void keyPressed(KeyEvent keyEvent)
  {
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

    return _backgroundImage;
  }

  public static BufferedImage loadSprite(String fileName)
  {
    BufferedImage _bImg = Instance.spriteMap.get(fileName);

    if (_bImg == null) {
      try {
        _bImg = ImageIO.read(Instance.getClass().getResourceAsStream("/Sprites/" + fileName));
        Instance.spriteMap.put(fileName, _bImg);
      } catch (Exception e) {
        System.out.println("ERROR: " + fileName + " not found");
      }
    }

    return _bImg;
  }
}
