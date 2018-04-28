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
import java.util.Map;


public abstract class DisplayableElement {
  protected Dimension dimension = new Dimension(0, 0);
  private HashMap<String, BufferedImage> spriteCache = new HashMap<>();
  private HashMap<String, Clip> soundCache = new HashMap<>();
  private Clip loopingSound;

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

  // Only one sound can be looping at a time.
  public void playSoundLooping(String fileName) {
    if (loopingSound != null) {
      loopingSound.stop();
    }
    try {
      InputStream fileStream = getClass().getResourceAsStream("/sounds/" + fileName);
      Clip clip = AudioSystem.getClip();
      clip.open(AudioSystem.getAudioInputStream(fileStream));
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      loopingSound = clip;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stopSounds() {
    for (Map.Entry<String, Clip> entry : soundCache.entrySet()) {
      Clip clip = entry.getValue();
      clip.stop();
    }

    loopingSound.stop();
  }
}
