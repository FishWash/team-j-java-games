package Scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// GamePanel runs on a thread and updates its display every frame.
public class GamePanel extends JPanel implements Runnable
{
  private GameWorld gameWorld;
  final long frameLength = 10;

  public GamePanel(Dimension dimension, GameWorld gameWorld)
  {
    super.setSize(dimension);
    this.gameWorld = gameWorld;
  }

  @Override
  public void run()
  {
    while (true) {
      repaint();
      try {
        Thread.sleep(frameLength);
      } catch (Exception e) {
        // do catch stuff
      }
    }
  }

  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    BufferedImage _currentGameImage = gameWorld.getCurrentImage();
    graphics.drawImage(_currentGameImage, 0, 0, null);
  }
}
