package scripts.gameWorlds;

import scripts.GamePanel;
import scripts.TankGameApplication;
import scripts.gameObjects.HealthPad;
import scripts.gameObjects.TankSpawner;
import scripts.gameObjects.pickups.HealthPickup;
import scripts.gameObjects.pickups.MachineGunPickup;
import scripts.gameWorlds.GameWorld;
import scripts.utility.Camera;
import scripts.utility.PlayerCamera;
import scripts.utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TankBattleWorld extends GameWorld {

  private PlayerCamera playerOneCamera;
  private PlayerCamera playerTwoCamera;
  private TankSpawner playerOneSpawner;
  private TankSpawner playerTwoSpawner;

  protected void initialize() {
    GameWorld.instance = this;
    dimension = new Dimension(1024, 1024);
    collisionHandler.readMapFile("CollisionTestMap.txt", TILE_SIZE);
    drawBackgroundImage("CollisionTestMap.txt", loadSprite("background_tile.png"),
                        loadSprite("wall_indestructible2.png"));
    playerOneSpawner = (TankSpawner) instantiate( new TankSpawner(new Vector2(128, 128), Player.One) );
    playerTwoSpawner = (TankSpawner) instantiate( new TankSpawner(
            new Vector2(dimension.width-128, dimension.height-128), Player.Two) );

    instantiate( new HealthPad(new Vector2(128, 128), Player.One) );
    instantiate( new HealthPad(new Vector2(dimension.width-128, dimension.height-128), Player.Two) );
    instantiate(new MachineGunPickup(new Vector2(dimension.width-128, 128)) );
    instantiate( new HealthPickup(new Vector2(128, dimension.height-128)) );

    playerOneCamera = new PlayerCamera(GameWorld.Player.One);
    playerTwoCamera = new PlayerCamera(GameWorld.Player.Two);

    playSoundLooping("Defense Line.wav");

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Pause);
  }

  public void displayOnGraphics(Graphics graphics) {
    int playerDisplayWidth = (TankGameApplication.windowDimension.width / 2);
    int playerDisplayHeight = TankGameApplication.windowDimension.height;
    BufferedImage currentImage = getCurrentImage();

    try {
      BufferedImage p1Display = playerOneCamera.getPlayerDisplay(currentImage, playerDisplayWidth, playerDisplayHeight);
      BufferedImage p2Display = playerTwoCamera.getPlayerDisplay(currentImage, playerDisplayWidth, playerDisplayHeight);
      BufferedImage minimap = Camera.getMinimapDisplay(currentImage);

      graphics.drawImage(p1Display, 0, 0, null);
      graphics.drawImage(p2Display, playerDisplayWidth, 0, null);

      graphics.drawImage(minimap, playerDisplayWidth - minimap.getWidth() / 2,
                         playerDisplayHeight - minimap.getHeight(), null);
      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setStroke(new BasicStroke(4));
      graphics2D.setColor(Color.BLACK);
      graphics2D.drawLine(playerDisplayWidth, 0, playerDisplayWidth,playerDisplayHeight - minimap.getHeight());
      graphics2D.drawRect(playerDisplayWidth - minimap.getWidth() / 2, playerDisplayHeight - minimap.getHeight(),
                          minimap.getWidth(), minimap.getHeight());

    } catch (Exception e) {
      System.out.println("ERROR in TankBattleWorld: " + e);
    }
  }

  public int getLives(GameWorld.Player player){
    if(player == Player.One){
      return playerOneSpawner.getLives();
    }
    else if(player == Player.Two){
      return playerTwoSpawner.getLives();
    } else {
      return 0;
    }
  }
}
