package scripts;

import scripts.gameObjects.HealthPad;
import scripts.utility.Camera;
import scripts.utility.PlayerCamera;
import scripts.utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TankBattleWorld extends GameWorld {

  private PlayerCamera playerOneCamera;
  private PlayerCamera playerTwoCamera;

  protected void initialize() {
    GameWorld.instance = this;
    dimension = new Dimension(1024, 1024);
    collisionHandler.readMapFile("CollisionTestMap.txt", TILE_SIZE);
    drawBackgroundImage("CollisionTestMap.txt", loadSprite("background_tile.png"),
                        loadSprite("wall_indestructible2.png"));

    instantiate( new TankSpawner(new Vector2(128, 128), Player.One) );
    instantiate( new TankSpawner(new Vector2(896, 896), Player.Two) );

    instantiate( new HealthPad(new Vector2(128, 128), Player.One) );
    instantiate( new HealthPad(new Vector2(896, 896), Player.Two) );

    playerOneCamera = new PlayerCamera(GameWorld.Player.One);
    playerTwoCamera = new PlayerCamera(GameWorld.Player.Two);
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
}
