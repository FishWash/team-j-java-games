package utility;

import general.GameWorld;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SpriteHandler {
  private static SpriteHandler instance;

  public SpriteHandler() {
    instance = this;
  }

  private HashMap<String, BufferedImage> spriteCache = new HashMap<>();

  public static SpriteHandler getInstance() {
    return instance;
  }

  public BufferedImage loadSprite(String fileName) {
    BufferedImage image = spriteCache.get(fileName);
    if (image == null) {
      try {
        image = ImageIO.read(getClass().getResourceAsStream(
                GameWorld.getInstance().getSpritePath() + fileName));
        spriteCache.put(fileName, image);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return image;
  }
}
