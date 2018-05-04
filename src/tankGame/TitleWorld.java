package tankGame;

import general.GamePanel;
import tankGame.gameObjects.TankSpawner;
import utility.Timer;
import utility.UI;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleWorld extends TankGameWorld {
  BufferedImage foregroundImage;
  int flashDelay = 48;
  Timer flashTimer = new Timer(flashDelay);
  boolean flashOn;

  protected void initialize() {
    dimension = new Dimension(800, 600);
    collisionHandler.readMapFile("TitleMap.txt", TILE_SIZE);
    drawBackgroundImage("TitleMap.txt", loadSprite("background_tile.png"),
                        loadSprite("wall_indestructible2.png"));
    drawForegroundImage();
    instantiate( new TankSpawner(new Vector2(128, dimension.height-128), Player.One) );
    instantiate( new TankSpawner(new Vector2(dimension.width-128, dimension.height-128), Player.Two) );

    playSoundLooping("Off Limits.wav");

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Start);
    GamePanel.getInstance().setEscapeFunction(GamePanel.EscapeFunction.Exit);
  }

  public void display(Graphics graphics) {
    BufferedImage currentImage = getCurrentImage();
    try {
      graphics.drawImage(currentImage, 0, 0, null);
      graphics.drawImage(foregroundImage, 0, 0, null);
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (flashTimer.isDone()) {
      flashOn = !flashOn;
      flashTimer.set(flashDelay);
    }

    if (flashOn) {
      Font font = new Font("Impact", Font.PLAIN, 48);
      UI.drawPositionedTextImage((Graphics2D)graphics, "Press Space to start", Color.WHITE, font,
                                 dimension.width, dimension.height, 0.5, 0.6);
    }
  }

  @Override
  protected void drawBackgroundImage(String mapFileName, BufferedImage backgroundTile, BufferedImage wallImage) {
    super.drawBackgroundImage(mapFileName, backgroundTile, wallImage);
    Graphics2D backgroundGraphics2D = (Graphics2D) backgroundImage.getGraphics();

    Font font = new Font("Impact", Font.PLAIN,  32);
    UI.drawPositionedTextImage(backgroundGraphics2D, "WASD to move", Color.WHITE, font,
                               dimension.width, dimension.height, 0.2, 0.85);
    UI.drawPositionedTextImage(backgroundGraphics2D, "R to shoot", Color.WHITE, font,
                               dimension.width, dimension.height, 0.2, 0.9);
    UI.drawPositionedTextImage(backgroundGraphics2D, "IJKL to move", Color.WHITE, font,
                               dimension.width, dimension.height, 0.8, 0.85);
    UI.drawPositionedTextImage(backgroundGraphics2D, "P to shoot", Color.WHITE, font,
                               dimension.width, dimension.height, 0.8, 0.9);
  }

  private void drawForegroundImage() {
    foregroundImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(),
                                        BufferedImage.TYPE_INT_ARGB);
    Graphics foregroundGraphics = foregroundImage.getGraphics();
    Font font = new Font("Impact", Font.PLAIN,  128);
    UI.drawPositionedTextImage((Graphics2D) foregroundGraphics, "TANK GAME", Color.WHITE, font,
                               dimension.width, dimension.height, 0.5, 0.3);
  }
}
