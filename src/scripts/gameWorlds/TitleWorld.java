package scripts.gameWorlds;

import scripts.GamePanel;
import scripts.gameObjects.TankSpawner;

import scripts.gameWorlds.GameWorld;
import scripts.utility.Timer;
import scripts.utility.UI;
import scripts.utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleWorld extends GameWorld {
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
  }

  public void displayOnGraphics(Graphics graphics) {
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

  private void drawForegroundImage() {
    foregroundImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(),
                                        BufferedImage.TYPE_INT_ARGB);
    Graphics foregroundGraphics = foregroundImage.getGraphics();
    Font font = new Font("Impact", Font.PLAIN,  128);
    UI.drawPositionedTextImage((Graphics2D) foregroundGraphics, "TANK GAME", Color.WHITE, font,
                               dimension.width, dimension.height, 0.5, 0.3);
  }
}
