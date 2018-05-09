package tankGame;

import general.GamePanel;
import general.GameWorld;
import tankGame.gameObjects.PickupSpawner;
import tankGame.gameObjects.TankSpawner;
import utility.*;
import utility.Camera;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TankDeathmatch extends TankGameWorld implements ClockListener {

  private PlayerCamera playerOneCamera, playerTwoCamera;
  private TankSpawner playerOneSpawner, playerTwoSpawner;
  private Switch flashSwitch = new Switch(48);

  protected void initialize() {
    GameWorld.instance = this;
    Clock.getInstance().addClockListener(this);
    dimension = new Dimension(1024, 1024);
    CollisionHandler.getInstance().readMapFile("CollisionTestMap.txt", TILE_SIZE);
    drawBackgroundImage("CollisionTestMap.txt",
                        SpriteHandler.getInstance().loadSprite("background_tile.png"),
                        SpriteHandler.getInstance().loadSprite("wall_indestructible2.png"));
    playerOneSpawner = (TankSpawner) instantiate( new TankSpawner(new Vector2(128, 128), Player.One) );
    playerTwoSpawner = (TankSpawner) instantiate( new TankSpawner(
            new Vector2(dimension.width-128, dimension.height-128), Player.Two) );

    instantiate(new PickupSpawner(new Vector2(dimension.width-96, 96)) );
    instantiate( new PickupSpawner(new Vector2(96, dimension.height-96)) );
    instantiate( new PickupSpawner(new Vector2(512, 512)));

    playerOneCamera = new PlayerCamera(TankGameWorld.Player.One);
    playerTwoCamera = new PlayerCamera(TankGameWorld.Player.Two);

    SoundHandler.getInstance().playSoundLooping("Defense Line.wav");
    SoundHandler.getInstance().loadSound("kazoocheer.wav");

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Pause);
    GamePanel.getInstance().setEscapeFunction(GamePanel.EscapeFunction.Pause);
  }

  public void display(Graphics graphics) {
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

      if (gameOver && flashSwitch.isOn()) {
        Font font = new Font("Impact", Font.PLAIN, 48);
        graphics.setColor(Color.WHITE);
        UI.drawPositionedText(graphics, "Press Space to Start", font,
                              0.5, 0.6);
      }
    } catch (Exception e) {
      System.out.println("ERROR in TankDeathmatch: " + e);
    }

  }

  public void update() {
    if(!gameOver && (playerOneSpawner.getLives() == 0 || playerTwoSpawner.getLives() == 0)) {
      gameOver = true;
      GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Start);
      SoundHandler.getInstance().playSound("kazoocheer.wav");
      if (playerOneSpawner.getLives() == 0 && playerTwoSpawner.getLives() == 0) {
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

  public int getLives(TankGameWorld.Player player){
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
