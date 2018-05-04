package utility;

import java.awt.image.BufferedImage;

public class AnimatedSprite implements ClockListener {
  private MultiSprite multiSprite;
  private BufferedImage currentSprite;
  private Timer animationTimer = new Timer();
  private int animationFrameLength;
  private int animationIndex = 0;
  private boolean loop;
  private boolean reverse;

  public AnimatedSprite(MultiSprite multiSprite, int animationFrameLength) {
    this.multiSprite = multiSprite;
    this.animationFrameLength = animationFrameLength;
    Clock.getInstance().addClockListener(this);
  }

  public void update() {
    if (animationTimer.isDone()) {
      if (!reverse) {
        if (animationIndex < multiSprite.getNumSubSprites()) {
          currentSprite = multiSprite.getSubSprite(animationIndex);
          animationIndex++;
          animationTimer.set(animationFrameLength);
        }
        else if (loop) {
          currentSprite = multiSprite.getSubSprite(0);
          animationIndex = 1;
          animationTimer.set(animationFrameLength);
        }
        else {
          currentSprite = null;
        }
      }
      else {
        if (animationIndex > -1) {
          currentSprite = multiSprite.getSubSprite(animationIndex);
          animationIndex--;
          animationTimer.set(animationFrameLength);
        }
        else if (loop) {
          currentSprite = multiSprite.getSubSprite(multiSprite.getNumSubSprites()-1);
          animationIndex = multiSprite.getNumSubSprites()-2;
          animationTimer.set(animationFrameLength);
        }
        else {
          currentSprite = null;
        }
      }
    }
  }

  public void setLoop(boolean loop) {
    this.loop = loop;
  }

  public void setReverse(boolean reverse) {
    this.reverse = reverse;
  }

  public BufferedImage getCurrentSprite() {
    return currentSprite;
  }
}
