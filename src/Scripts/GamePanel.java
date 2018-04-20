package Scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    this.addKeyListener(gameWorld);
    this.setFocusable(true);
  }

  @Override
  public void run()
  {
    while (true) {
      gameWorld.update();
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
    gameWorld.gameDisplay(graphics);
  }
}
