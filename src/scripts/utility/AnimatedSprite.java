package scripts.utility;

import java.awt.image.BufferedImage;

public class AnimatedSprite implements ClockListener {
  private MultiSprite multiSprite;
  private BufferedImage currentSprite;
  private Timer animationTimer = new Timer();
  private int animationFrameLength;
  private int animationIndex = 0;

  public AnimatedSprite(MultiSprite multiSprite, int animationFrameLength) {
    this.multiSprite = multiSprite;
    this.animationFrameLength = animationFrameLength;
  }

  public void update() {
    if (animationIndex < multiSprite.getNumSubSprites()) {
      if (animationTimer.isDone()) {
        currentSprite = multiSprite.getSubSprite(animationIndex);
        animationIndex++;
        animationTimer.set(animationFrameLength);
      }
    }
    else {
      currentSprite = null;
    }
  }

  public BufferedImage getCurrentSprite() {
    return currentSprite;
  }
}
