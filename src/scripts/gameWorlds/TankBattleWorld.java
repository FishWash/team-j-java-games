package scripts.gameWorlds;

import scripts.GamePanel;
import scripts.TankGameApplication;
import scripts.gameObjects.HealthPad;
import scripts.gameObjects.TankSpawner;
import scripts.utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TankBattleWorld extends GameWorld implements ClockListener {

  private PlayerCamera playerOneCamera;
  private PlayerCamera playerTwoCamera;
  private TankSpawner playerOneSpawner;
  private TankSpawner playerTwoSpawner;
  private boolean gameOver = false;
  int flashDelay = 48;
  Timer flashTimer = new Timer(flashDelay);
  boolean flashOn;

  protected void initialize() {
    GameWorld.instance = this;
    Clock.getInstance().addClockListener(this);
    dimension = new Dimension(1024, 1024);
    collisionHandler.readMapFile("CollisionTestMap.txt", TILE_SIZE);
    drawBackgroundImage("CollisionTestMap.txt", loadSprite("background_tile.png"),
                        loadSprite("wall_indestructible2.png"));
    playerOneSpawner = (TankSpawner) instantiate( new TankSpawner(new Vector2(128, 128), Player.One) );
    playerTwoSpawner = (TankSpawner) instantiate( new TankSpawner(
            new Vector2(dimension.width-128, dimension.height-128), Player.Two) );

    instantiate( new HealthPad(new Vector2(128, 128), Player.One) );
    instantiate( new HealthPad(new Vector2(896, 896), Player.Two) );

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

      if(gameOver){
        if (flashTimer.isDone()) {
          flashOn = !flashOn;
          flashTimer.set(flashDelay);
        }

        if (flashOn) {
          Font font = new Font("Impact", Font.PLAIN, 30);
          UI.drawPositionedTextImage(graphics2D, "Press space to restart", Color.WHITE, font,
                  playerDisplayWidth, playerDisplayHeight, 1, 0.7);
        }
        GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Start);
      }
    } catch (Exception e) {
      System.out.println("ERROR in TankBattleWorld: " + e);
    }

  }

  public void update(){
    if(playerOneSpawner.getLives() == 0 || playerTwoSpawner.getLives() == 0){
      gameOver = true;
      if(playerOneSpawner.getLives() == 0 && playerTwoSpawner.getLives() == 0){
        playerOneCamera.setDisplayText(PlayerCamera.DisplayText.Draw);
        playerTwoCamera.setDisplayText(PlayerCamera.DisplayText.Draw);
      } else if(playerOneSpawner.getLives() == 0){
        playerOneCamera.setDisplayText(PlayerCamera.DisplayText.Lose);
        playerTwoCamera.setDisplayText(PlayerCamera.DisplayText.Win);
      } else if(playerTwoSpawner.getLives() == 0){
        playerOneCamera.setDisplayText(PlayerCamera.DisplayText.Win);
        playerTwoCamera.setDisplayText(PlayerCamera.DisplayText.Lose);
      }
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
