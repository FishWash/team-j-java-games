package Scripts;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MultiSprite
{
  private BufferedImage spriteStrip;
  private int numSubsprites;
  private int subspriteWidth;
  private HashMap<Integer, BufferedImage> subspriteCache;

  public MultiSprite(BufferedImage spriteStrip, int numSubsprites)
  {
    this.spriteStrip = spriteStrip;
    this.numSubsprites = numSubsprites;
    subspriteWidth = spriteStrip.getWidth() / numSubsprites;
    subspriteCache = new HashMap<>();
  }

  public BufferedImage getSubsprite(int index)
  {
    BufferedImage _subsprite = subspriteCache.get(index);

    if (_subsprite == null) {
      if (index < numSubsprites) {
        _subsprite = spriteStrip.getSubimage( subspriteWidth*index, 0, subspriteWidth, spriteStrip.getHeight() );
        subspriteCache.put(index, _subsprite);
      }
      else {
        System.out.println("Subsprite out of bounds.");
      }
    }

    return _subsprite;
  }

  public int getNumSubsprites()
  {
    return numSubsprites;
  }

  public void setSpriteStrip(BufferedImage spriteStrip)
  {
    this.spriteStrip = spriteStrip;
    subspriteWidth = spriteStrip.getWidth() / numSubsprites;
    subspriteCache = new HashMap<>();
  }

  public void setNumSubsprites(int numSubsprites)
  {
    this.numSubsprites = numSubsprites;
    subspriteWidth = spriteStrip.getWidth() / numSubsprites;
  }
}
