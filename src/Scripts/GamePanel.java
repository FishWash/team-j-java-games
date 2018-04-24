package Scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

// GamePanel runs on a thread and updates its display every frame.
public class GamePanel extends JPanel implements ClockObserver
{
  private Clock clock;
  private Thread clockThread;
  private GameWorld gameWorld;
  private KeyInputHandler keyInputHandler;

  public GamePanel(Dimension dimension)
  {
    clock = new Clock();
    Thread clockThread = new Thread(clock);
    clock.addClockObserver(this);
    keyInputHandler = new KeyInputHandler();
    this.addKeyListener(keyInputHandler);
    gameWorld = new GameWorld();

    super.setSize(dimension);
    this.setFocusable(true);
    clockThread.start();
  }

  public void update() {
    repaint();
  }

  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    gameWorld.getDisplay(graphics);
  }
}
