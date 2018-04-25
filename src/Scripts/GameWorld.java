package Scripts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld
{
  public enum Player {Neutral, One, Two}
  public final int TILE_SIZE = 32;

  private static GameWorld instance;
  private Dimension dimension = new Dimension(1024, 1024);

  private CopyOnWriteArrayList<DestructibleWall> destructibleWalls  = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Tank> tanks                          = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Projectile> projectiles              = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Explosion> explosions                = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Damageable> damageables              = new CopyOnWriteArrayList<>();
  private HashMap<String, BufferedImage> spriteCache                = new HashMap<>();
  private CollisionHandler collisionHandler                         = new CollisionHandler();

  private BufferedImage backgroundImage;
  private Tank p1_tank, p2_tank;

  // Initialization
  public GameWorld()
  {
    instance = this;
    initialize();
  }

  public void initialize()
  {
    collisionHandler.readMapFile("CollisionTestMap.txt", TILE_SIZE);
    backgroundImage = drawBackgroundImage("CollisionTestMap.txt",
                                          loadSprite("background_tile.png"),
                                          loadSprite("wall_indestructible2.png"));

    // Tank Initialization
    p1_tank = (Tank) instantiate(new Tank( new Vector2(96, 96), Player.One ));
    p2_tank = (Tank) instantiate(new Tank( new Vector2(864, 864), Player.Two ));

    p1_tank.setSprite("Tank_blue_basic_strip60.png");
    p2_tank.setSprite("Tank_red_basic_strip60.png");
  }

//  private void drawB(String fileName){
//    try {
//      File file = new File(fileName);
//      fileName = file.getAbsolutePath();
//      FileReader fileReader = new FileReader(fileName);
//      BufferedReader bufferedReader = new BufferedReader(fileReader);
//      String line = bufferedReader.readLine();
//
//      for (int i=0; i<yMax && line!=null; i++) {
//        for (int j=0; j<xMax && j<line.length(); j++) {
//          int tileInt = line.charAt(j) - '0';
//          if (tileInt >= 0 && tileInt <= 2) {
//            mapGrid[i][j] = tileInt;
//          }
//        }
//        line = bufferedReader.readLine();
//      }
//
//    } catch(Exception e){
//      System.out.println(e.getMessage());
//    }
//
//    // Add outer walls
//    for (int i=0; i<mapGrid.length; i++) {
//      mapGrid[i][0] = 1;
//      mapGrid[i][mapGrid[i].length-1] = 1;
//    }
//    for (int j=0; j<mapGrid[0].length; j++) {
//      mapGrid[0][j] = 1;
//      mapGrid[mapGrid.length-1][j] = 1;
//    }
//
////    for (int i=0; i<mapGrid.length; i++) {
////      for (int j=0; j<mapGrid[i].length; j++) {
////        System.out.print(mapGrid[i][j] + " ");
////      }
////      System.out.println();
////    }
//
//    initializeWalls();
//    initializeWallCollidables();
//  }

//  private void initializeWalls() {
//    for (int i=0; i<mapGrid.length; i++) {
//      for (int j=0; j<mapGrid[i].length; j++) {
//        if (mapGrid[i][j] == 1) {
//          instantiate(new Wall(new Vector2(j*TILE_SIZE, i*TILE_SIZE)));
//        }
//        else if (mapGrid[i][j] == 2) {
//          instantiate(new DestructibleWall(new Vector2(j*TILE_SIZE, i*TILE_SIZE)));
//        }
//      }
//    }
//  }

//  private void initializeWallCollidables() {
//    for (int i=0; i<mapGrid.length; i++) {
//      for (int j=0; j<mapGrid[i].length; j++) {
//        if (mapGrid[i][j] == 1) {
//          CollidableGameObject collidableGameObject = new CollidableGameObject(new Vector2(j*TILE_SIZE, i*TILE_SIZE));
//          int collidableLength = 0;
//          do {
//            collidableLength++;
//            j++;
//          } while (j<mapGrid[i].length && mapGrid[i][j] == 1);
//          collidableGameObject.setTriggerSize(new Vector2(collidableLength*TILE_SIZE, TILE_SIZE));
//          collidables.add(collidableGameObject);
//        }
//      }
//    }
//  }

  public static GameWorld getInstance() {
    return instance;
  }

  // drawBackgroundImage draws background tiles and indestructible walls onto a BufferedImage and returns it.
  private BufferedImage drawBackgroundImage(String mapFileName,
                                                   BufferedImage backgroundTile, BufferedImage wallImage) {
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

  public BufferedImage getCurrentImage() {
    BufferedImage currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics currentImageGraphics = currentImage.createGraphics();

    currentImageGraphics.drawImage(backgroundImage, 0, 0, null);
    for (DestructibleWall dw : destructibleWalls) {
      dw.drawSprite(currentImageGraphics);
    }
    for (Projectile p : projectiles) {
      p.drawSprite(currentImageGraphics);
    }
    for (Tank t : tanks) {
      t.drawSprite(currentImageGraphics);
    }
    for (Explosion e : explosions) {
      e.drawSprite(currentImageGraphics);
    }

    return currentImage;
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
    else if (gameObject instanceof Projectile) {
      instance.projectiles.add((Projectile) gameObject);
    }
    else if (gameObject instanceof Tank) {
      instance.tanks.add((Tank) gameObject);
    }
    else if (gameObject instanceof Explosion) {
      instance.explosions.add((Explosion) gameObject);
    }

    if (gameObject instanceof Collidable) {
      instance.collisionHandler.addCollidable((Collidable) gameObject);
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
    else if (gameObject instanceof Projectile) {
      instance.projectiles.remove(gameObject);
    }
    else if (gameObject instanceof Tank) {
      instance.tanks.remove(gameObject);
    }
    else if (gameObject instanceof Explosion) {
      instance.explosions.remove(gameObject);
    }

    if (gameObject instanceof CollidableGameObject) {
      instance.collisionHandler.removeCollidable((Collidable) gameObject);
    }
    if (gameObject instanceof Damageable) {
      instance.damageables.remove(gameObject);
    }
    if (gameObject instanceof ClockObserver) {
      Clock.getInstance().removeClockObserver((ClockObserver) gameObject);
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

  public static BufferedImage loadSprite(String fileName) {
    BufferedImage _bImg = instance.spriteCache.get(fileName);
    if (_bImg == null) {
      try {
        _bImg = ImageIO.read(instance.getClass().getResourceAsStream("/Sprites/" + fileName));
        instance.spriteCache.put(fileName, _bImg);
      } catch (Exception e) {
        System.out.println("ERROR in GameWorld: " + fileName + " not found");
        }
    }
    return _bImg;
  }
}
