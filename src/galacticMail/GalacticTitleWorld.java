package galacticMail;

import galacticMail.gameObjects.Asteroid;
import general.GamePanel;
import utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GalacticTitleWorld extends GalacticMailWorld {

  public GalacticTitleWorld() {
    super(0);
  }

  private Switch flashSwitch = new Switch(48);

  protected void initialize() {
    dimension = new Dimension(800, 600);
    drawBackground(SpriteHandler.getInstance().loadSprite("Background1.png"));

    for (int i=0; i<16; i++) {
      Vector2 randomPosition = RandomNumberGenerator.getRandomPosition(
              0, 0, dimension.width, dimension.height );
      double randomRotation = RandomNumberGenerator.getRandomDouble(0, 360);
      instantiate(new Asteroid(randomPosition, randomRotation));
    }

    GamePanel.getInstance().setSpaceFunction(GamePanel.SpaceFunction.Restart);
    GamePanel.getInstance().setEscapeFunction(GamePanel.EscapeFunction.Exit);

    SoundHandler.getInstance().playSoundLooping("Pulse Loop.wav");
  }

  protected void drawTitle(BufferedImage currentImage) {
    Graphics2D g2D = (Graphics2D) currentImage.getGraphics();
    BufferedImage title = SpriteHandler.getInstance().loadSprite("Title.png");
    g2D.drawImage( title, (int)(dimension.getWidth()/2) - (title.getWidth()/2),
        (int)(dimension.getHeight() * 0.4) - (title.getHeight() / 2), null);

    if (flashSwitch.isOn()) {
      g2D.setColor(Color.WHITE);
      Font font = new Font("Impact", Font.PLAIN,  48);
      UI.drawPositionedText(g2D, "Press Space to start", font, 0.5, 0.75);
    }
  }

  @Override
  protected BufferedImage getCurrentImage(){
    BufferedImage currentImage = super.getCurrentImage();
    drawTitle(currentImage);

    return currentImage;
  }

}
