package scripts.utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public abstract class DisplayableElement {
  protected Dimension dimension = new Dimension(0, 0);
  private HashMap<String, BufferedImage> spriteCache = new HashMap<>();


  public abstract void displayOnGraphics(Graphics graphics);

  public Dimension getDimension() {
    return dimension;
  }

  public BufferedImage loadSprite(String fileName) {
    BufferedImage _bImg = spriteCache.get(fileName);
    if (_bImg == null) {
      try {
        _bImg = ImageIO.read(getClass().getResourceAsStream("/sprites/" + fileName));
        spriteCache.put(fileName, _bImg);
      } catch (Exception e) {
        System.out.println("ERROR in DisplayableElement: " + fileName + " not found");
      }
    }
    return _bImg;
  }
}