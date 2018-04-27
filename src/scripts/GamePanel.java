package scripts;

import scripts.utility.Clock;
import scripts.utility.ClockListener;
import scripts.utility.KeyInputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

// GamePanel runs on a thread and updates its display every frame.
public class GamePanel extends JPanel implements KeyListener, ClockListener
{
  private Clock clock;
  private Thread clockThread;
  private GameWorld gameWorld;
  private KeyInputHandler keyInputHandler;
  private BufferedImage pauseImage;

  private int pauseKeyCode1 = KeyEvent.VK_SPACE, pauseKeyCode2 = KeyEvent.VK_ESCAPE;

  public GamePanel(Dimension dimension) {
    this.addKeyListener(this);
    keyInputHandler = new KeyInputHandler();
    this.addKeyListener(keyInputHandler);

    clock = new Clock();
    clock.addClockObserver(this);
    clockThread = new Thread(clock);
    gameWorld = new TankBattleWorld();

    super.setSize(dimension);
    this.setFocusable(true);
    clockThread.start();
  }

  public void update() {
    repaint();
  }

  public void keyPressed(KeyEvent keyEvent) {
    int keyCode = keyEvent.getExtendedKeyCode();
    if (keyCode == pauseKeyCode1 || keyCode == pauseKeyCode2) {
      pause();
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
    gameWorld = new TankBattleWorld();
    clockThread.start();
  }

  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    if (gameWorld != null) {
      gameWorld.displayOnGraphics(graphics);
    }

    if (clock.getPaused()) {
      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setColor(Color.WHITE);
      Font font = new Font("Impact", Font.PLAIN, 64);
      graphics2D.setFont(font);
      FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
      String textToDisplay = "Paused";
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      int xPos = ((int)super.getSize().getWidth() - fontMetrics.stringWidth(textToDisplay)) / 2;
      int yPos = ((int)super.getSize().getHeight() + fontMetrics.getHeight()) / 2;
      graphics2D.drawString(textToDisplay, xPos, yPos);
    }
  }
}
