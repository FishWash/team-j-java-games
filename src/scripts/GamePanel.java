package scripts;

import scripts.utility.Clock;
import scripts.utility.ClockListener;
import scripts.utility.KeyInputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// GamePanel runs on a thread and updates its display every frame.
public class GamePanel extends JPanel implements KeyListener, ClockListener
{
  private Clock clock;
  private Thread clockThread;
  private GameWorld gameWorld;
  private KeyInputHandler keyInputHandler;

  private int pauseKeyCode = KeyEvent.VK_SPACE, restartKeyCode = KeyEvent.VK_ESCAPE;

  public GamePanel(Dimension dimension) {
    this.addKeyListener(this);
    keyInputHandler = new KeyInputHandler();
    this.addKeyListener(keyInputHandler);

    clock = new Clock();
    clock.addClockObserver(this);
    clockThread = new Thread(clock);
    gameWorld = new GameWorld();

    super.setSize(dimension);
    this.setFocusable(true);
    clockThread.start();
  }

  public void update() {
    repaint();
  }

  public void keyPressed(KeyEvent keyEvent) {
    if (keyEvent.getExtendedKeyCode() == pauseKeyCode) {
      pause();
    }
    if (keyEvent.getExtendedKeyCode() == restartKeyCode) {
      restart();
    }
  }
  public void keyReleased(KeyEvent keyEvent) {}
  public void keyTyped(KeyEvent keyEvent) {}

  private void pause() {
    clock.pressPause();
    repaint();
  }

  private void restart() {
    gameWorld = null;
    clock.stop();
    clock = new Clock();
    clock.addClockObserver(this);
    clockThread = new Thread(clock);
    gameWorld = new GameWorld();
    clockThread.start();
  }

  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    if (gameWorld != null) {
      gameWorld.getDisplay(graphics);
    }

    if (clock.getPaused()) {
      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setColor(Color.WHITE);
      Font font = new Font("Helvetica", Font.BOLD, 64);
      graphics2D.setFont(font);
      FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
      String textToDisplay = "Paused";
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      int xPos = ((int)super.getSize().getWidth() - fontMetrics.stringWidth(textToDisplay)) / 2;
      int yPos = ((int)super.getSize().getHeight() - fontMetrics.getHeight()) / 2;
      graphics2D.drawString(textToDisplay, xPos, yPos);
    }
  }
}
