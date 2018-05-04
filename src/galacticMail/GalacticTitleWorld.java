package galacticMail;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GalacticTitleWorld extends GalacticMailWorld{

  protected void initialize(){
    dimension = new Dimension(800, 600);
    drawBackground(loadSprite("Background.png"));
  }

  protected void drawTitle(BufferedImage currentImage){
    Graphics2D g2D = (Graphics2D) currentImage.getGraphics();
    BufferedImage title = loadSprite("Title.png");
    g2D.drawImage( title, (int)(dimension.getWidth() / 2) - (title.getWidth() / 2), (int)(dimension.getHeight() / 3) - (title.getHeight() / 2), null);
  }

  @Override
  protected BufferedImage getCurrentImage(){
    BufferedImage currentImage = super.getCurrentImage();
    drawTitle(currentImage);

    return currentImage;
  }

}
