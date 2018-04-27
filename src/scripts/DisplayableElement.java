package scripts;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


public abstract class DisplayableElement {
  protected Dimension dimension = new Dimension(0, 0);
  private HashMap<String, BufferedImage> spriteCache = new HashMap<>();
  private HashMap<String, Clip> soundCache = new HashMap<>();

  public abstract void displayOnGraphics(Graphics graphics);

  public Dimension getDimension() {
    return dimension;
  }

  public static BufferedImage getScaledImage(BufferedImage imageToResize, double scale, int newWidth, int newHeight){
    BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    at.scale(scale, scale);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    scaledImage = scaleOp.filter(imageToResize, scaledImage);

    return scaledImage;
  }

  public BufferedImage loadSprite(String fileName) {
    BufferedImage image = spriteCache.get(fileName);
    if (image == null) {
      try {
        image = ImageIO.read(getClass().getResourceAsStream("/sprites/" + fileName));
        spriteCache.put(fileName, image);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return image;
  }

  public Clip loadSound(String fileName) {
    Clip clip = soundCache.get(fileName);
    if (clip == null) {
      try {
        InputStream fileStream = getClass().getResourceAsStream("/sounds/" + fileName);
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(fileStream));
        soundCache.put(fileName, clip);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return clip;
  }

  public void playSound(String fileName) {
    Clip clip = loadSound(fileName);
    if (clip != null) {
      clip.stop();
      clip.setFramePosition(0);
      clip.start();
    }
  }

  public void playSoundLooping(String fileName) {
    Clip clip = loadSound(fileName);
    if (clip != null) {
      clip.stop();
      clip.setFramePosition(0);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      clip.start();
    }
  }

  public void stopSound(String fileName) {
    Clip clip = loadSound(fileName);
    if (clip != null) {
      clip.stop();
    }
  }
}
