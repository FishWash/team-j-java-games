package tankGame;

import general.GamePanel;
import tankGame.gameObjects.TankSpawner;
import utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleWorld extends TankGameWorld {
  private BufferedImage foregroundImage;
  private Switch flashSwitch = new Switch(48);

  protected void initialize() {
    dimension = new Dimension(800, 600);
    CollisionHandler.getInstance().readMapFile("TitleMap.txt", TILE_SIZE);
    drawBackgroundImage("TitleMap.txt", SpriteHandler.getInstance().loadSprite("background_tile.png"),
                        SpriteHandler.getInstance().loadSprite("wall_indestructible2.png"));
    drawForegroundImage();
    instantiate( new TankSpawner(new Vector2(128, dimension.height-128), Player.One) );
    instantiate( new TankSpawner(new Vector2(dimension.width-128, dimension.height-128), Player.Two) );

    SoundHandler.getInstance().playSoundLooping("Off Limits.wav");

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

    if (flashSwitch.isOn()) {
      Font font = new Font("Impact", Font.PLAIN,  48);
      graphics.setColor(Color.WHITE);
      UI.drawPositionedText(graphics, "Press SPACE to start", font, 0.5, 0.6);
    }
  }

  @Override
  protected void drawBackgroundImage(String mapFileName, BufferedImage backgroundTile, BufferedImage wallImage) {
    super.drawBackgroundImage(mapFileName, backgroundTile, wallImage);
    Graphics backgroundGraphics = backgroundImage.getGraphics();

    Font font = new Font("Impact", Font.PLAIN,  32);
    backgroundGraphics.setColor(Color.WHITE);
    UI.drawPositionedText(backgroundGraphics, "WASD to move", font, 0.2, 0.85);
    UI.drawPositionedText(backgroundGraphics, "R to shoot", font, 0.2, 0.9);
    UI.drawPositionedText(backgroundGraphics, "IJKL to move", font, 0.8, 0.85);
    UI.drawPositionedText(backgroundGraphics, "P to shoot", font, 0.8, 0.9);
  }

  private void drawForegroundImage() {
    foregroundImage = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(),
                                        BufferedImage.TYPE_INT_ARGB);
    Graphics foregroundGraphics = foregroundImage.getGraphics();
    Font font = new Font("Impact", Font.PLAIN,  128);
    foregroundGraphics.setColor(Color.WHITE);
    UI.drawPositionedText(foregroundGraphics, "TANK GAME", font, 0.5, 0.3);
  }
}
