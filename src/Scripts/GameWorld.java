package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class GameWorld
{
  public static GameWorld Instance;

  private Dimension dimension;
  private BufferedImage backgroundImage; //image of GameWorld with background tiles and walls

  private ArrayList<Wall> walls;
  private ArrayList<DestructibleWall> destructibleWalls;
  private ArrayList<Tank> tanks;
  private ArrayList<Bullet> bullets;
  // explosions
  private ArrayList<Collidable> collidables;
  private ArrayList<Damageable> damageables;
  // observers

  private HashMap<String, Image> spriteMap;

  public GameWorld(Dimension dimension) {
    Instance = this;
    this.dimension = dimension;
    initialize();

  }

  public void initialize() {
    walls = new ArrayList<>();
    destructibleWalls = new ArrayList<>();
    tanks = new ArrayList<>();
    bullets = new ArrayList<>();
    collidables = new ArrayList<>();
    damageables = new ArrayList<>();

    backgroundImage = drawBackgroundImage();

    instantiate(new Tank(new Point(50, 950)));
    instantiate(new Tank(new Point(950, 950)));
  }

  public static BufferedImage loadSprite(String fileName)
  {
    BufferedImage _bImg = null;
    try {
      _bImg = ImageIO.read(Instance.getClass().getResourceAsStream("/Sprites/" + fileName));
    } catch (Exception e) {
      System.out.println("ERROR: " + fileName + " not found");
    }

    return _bImg;
  }

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

  public synchronized void gameDisplay(Graphics graphics){
    BufferedImage currentImage = getCurrentImage();
    int gameWidth = currentImage.getWidth();
    int playerDisplayWidth = (TankGameApplication.windowDimension.width / 2);
    int gameHeight = currentImage.getHeight();
    int playerDisplayHeight = TankGameApplication.windowDimension.height;
    Point tankOnePosition = tanks.get(0).getTankCenterPosition();
    Point tankTwoPosition = tanks.get(1).getTankCenterPosition();


    int p1DisplayX = Math.max(0, Math.min(gameWidth - playerDisplayWidth, tankOnePosition.x - playerDisplayWidth / 2));
    int p2DisplayX = Math.max(0, Math.min(gameWidth - playerDisplayWidth, tankTwoPosition.x - playerDisplayWidth / 2));

    int p1DisplayY = Math.max(0, Math.min(gameHeight - playerDisplayHeight, tankOnePosition.y - playerDisplayHeight / 2));
    int p2DisplayY = Math.max(0, Math.min(gameHeight - playerDisplayHeight, tankTwoPosition.y - playerDisplayHeight / 2));

    System.out.println(p1DisplayY);

    BufferedImage playerOneView = currentImage.getSubimage(p1DisplayX, p1DisplayY, playerDisplayWidth, playerDisplayHeight);
    BufferedImage playerTwoView = currentImage.getSubimage(p2DisplayX, p2DisplayY, 400, 600);
    graphics.drawImage(playerOneView, 0, 0, null);
    graphics.drawImage(playerTwoView, playerDisplayWidth, 0, null);
  }

  private void instantiate(GameObject gameObject) {
    if (gameObject instanceof Wall) {
      walls.add((Wall) gameObject);
    }
    else if (gameObject instanceof  DestructibleWall) {
      destructibleWalls.add((DestructibleWall) gameObject);
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
    readMap(file.getAbsolutePath());

    //addWall(1,new Point(0,0));
    for(Wall indestructibleWall : walls){
      indestructibleWall.drawSprite(_graphics);
    }
    for(DestructibleWall destructibleWall : destructibleWalls){
      destructibleWall.drawSprite(_graphics);
    }

    return _backgroundImage;
  }

  private void readMap(String file){
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
          addWall(wallNum, new Point(xPos * wallDimensions, yPos * wallDimensions));
        }
        yPos ++;
      }
    } catch(Exception e){
      System.out.println(e.getMessage());
    }

  }

  public void addWall(int wallNum, Point position){
    if(wallNum == '1'){
      instantiate(new Wall(position));
    }
    if(wallNum == '2'){
      instantiate(new DestructibleWall(position));
    }

  }
}
