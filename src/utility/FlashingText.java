package utility;

import general.GamePanel;

import java.awt.*;

public class FlashingText {
  protected static FlashingText instance;

  protected static int flashDelay = 48;
  protected static Timer flashTimer = new Timer(flashDelay);
  protected static boolean flashOn;

  public FlashingText(){
    instance = this;
    flashTimer = new Timer();
    flashOn = false;
  }

  public static void drawFlashingText(Graphics graphics, String textToDisplay, double heightProportion){
    if (flashTimer.isDone()) {
      flashOn = !flashOn;
      flashTimer.set(flashDelay);
    }

    if (flashOn) {
      Font font = new Font("Impact", Font.PLAIN, 48);
      UI.drawPositionedTextImage((Graphics2D)graphics, textToDisplay, Color.WHITE, font,
              GamePanel.getInstance().getSize(), 0.5, heightProportion);
    }
  }

}
