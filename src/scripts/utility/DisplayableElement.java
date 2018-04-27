package scripts.utility;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public abstract class DisplayableElement {
  protected Dimension dimension = new Dimension(0, 0);
  private HashMap<String, BufferedImage> spriteCache = new HashMap<>();
  private HashMap<String, AudioInputStream> soundCache = new HashMap<>();

  public abstract void displayOnGraphics(Graphics graphics);

  public Dimension getDimension() {
    return dimension;
  }

  public BufferedImage loadSprite(String fileName) {
    BufferedImage image = spriteCache.get(fileName);
    if (image == null) {
      try {
        image = ImageIO.read(getClass().getResourceAsStream("/sprites/" + fileName));
        spriteCache.put(fileName, image);
      } catch (Exception e) {
        System.out.println("ERROR in DisplayableElement: " + fileName + " not found");
      }
    }
    return image;
  }
  public AudioInputStream loadSound(String fileName) {
    AudioInputStream
  }
}
