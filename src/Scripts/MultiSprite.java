package Scripts;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MultiSprite {

  private BufferedImage spriteStrip;
  private int numSubSprites;
  private int subSpriteWidth;
  private HashMap<Integer, BufferedImage> subSpriteCache = new HashMap<>();

  public MultiSprite(BufferedImage spriteStrip, int numSubSprites) {
    this.spriteStrip = spriteStrip;
    this.numSubSprites = numSubSprites;
    if (spriteStrip != null) {
      subSpriteWidth = spriteStrip.getWidth() / numSubSprites;
    }
  }

  public BufferedImage getSubSprite(int index) {
    BufferedImage subSprite = subSpriteCache.get(index);
    if (subSprite == null) {
      if (index < numSubSprites) {
        try {
          subSprite = spriteStrip.getSubimage( subSpriteWidth*index, 0, subSpriteWidth, spriteStrip.getHeight() );
          subSpriteCache.put(index, subSprite);
        } catch (Exception e) {
          System.out.println("ERROR in MultiSprite: " + e);
        }
      }
      else {
        System.out.println("SubSprite out of bounds.");
      }
    }

    return subSprite;
  }

  public BufferedImage getSubSpriteByRotation(double rotation) {
    double angleStep = 360/this.getNumSubSprites();
    double spriteRotation = (360 + rotation + angleStep/2) % 360;
    int index = (int) (spriteRotation / angleStep);
    return getSubSprite(index);
  }

  public int getNumSubSprites()
  {
    return numSubSprites;
  }

  public void setSpriteStrip(BufferedImage spriteStrip) {
    this.spriteStrip = spriteStrip;
    subSpriteWidth = spriteStrip.getWidth() / numSubSprites;
    subSpriteCache = new HashMap<>();
  }

  public void setNumSubSprites(int numSubSprites) {
    this.numSubSprites = numSubSprites;
    subSpriteWidth = spriteStrip.getWidth() / numSubSprites;
  }
}
