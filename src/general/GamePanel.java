package general;

import utility.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

// general.GamePanel handles pause, play, and exit controls.
public abstract class GamePanel extends JPanel implements KeyListener, ClockListener
{
  public enum SpaceFunction {None, Start, Pause, Restart}
  public enum EscapeFunction {None, Pause, Exit}
  protected  String title = "";

  private static GamePanel instance;
  private SpaceFunction spaceFunction = SpaceFunction.None;
  private EscapeFunction escapeFunction = EscapeFunction.None;
  private BufferedImage pauseImage;

  protected Thread clockThread;

  private int spaceKeyCode = KeyEvent.VK_SPACE,
          escapeKeyCode = KeyEvent.VK_ESCAPE;

  public GamePanel(Dimension dimension) {
    instance = this;

    resetClock();
    new SpriteHandler();
    new SoundHandler();

    KeyInputHandler keyInputHandler = new KeyInputHandler();
    this.addKeyListener(keyInputHandler);
    this.addKeyListener(this);

    super.setSize(dimension);
    this.setFocusable(true);
    drawPauseImage();

    clockThread.start();
  }

  public static GamePanel getInstance() {
    return instance;
  }

  public void update() {
    repaint();
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    try {
      GameWorld.getInstance().display(graphics);
      if (Clock.getInstance().getPaused()) {
        graphics.drawImage(pauseImage, 0, 0, null);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setSpaceFunction(SpaceFunction spaceFunction) {
    this.spaceFunction = spaceFunction;
  }
  public void setEscapeFunction(EscapeFunction escapeFunction) {
    this.escapeFunction = escapeFunction;
  }

  public void keyPressed(KeyEvent keyEvent) {
    int keyCode = keyEvent.getExtendedKeyCode();
    if (keyCode == spaceKeyCode) {
      switch (spaceFunction) {
        case Pause:
          pause();
          break;
        case Start:
          start();
          break;
        case Restart:
          restart();
          break;
      }
    }
    if (keyCode == escapeKeyCode) {
      switch (escapeFunction) {
        case Pause:
          pause();
          break;
        case Exit:
          System.exit(0);
          break;
      }
    }
  }
  public void keyReleased(KeyEvent keyEvent) {}
  public void keyTyped(KeyEvent keyEvent) {}

  protected abstract void start();
  protected abstract void restart();

  protected void resetClock() {
    if (Clock.getInstance() != null) {
      Clock.getInstance().stop();
    }
    Clock clock = new Clock();
    clockThread = new Thread(clock);
    clock.addClockListener(this);
  }

  protected void pause() {
    Clock clock = Clock.getInstance();
    if (clock.getPaused()) {
      clock.setPaused(false);
      escapeFunction = EscapeFunction.Pause;
    }
    else {
      clock.setPaused(true);
      repaint();
      escapeFunction = EscapeFunction.Exit;
    }
  }

  private void drawPauseImage() {
    pauseImage = new BufferedImage((int)super.getSize().getWidth(), (int)super.getSize().getHeight(),
                                   BufferedImage.TYPE_INT_ARGB);
    Graphics2D pauseGraphics2D = (Graphics2D) pauseImage.getGraphics();
    Font font = new Font("Impact", Font.PLAIN, 64);

    UI.drawPositionedText(pauseGraphics2D, "Paused", font, 0.5, 0.4);
    font = new Font("Impact", Font.PLAIN, 32);
    UI.drawPositionedText(pauseGraphics2D, "(SPACE to resume)", font, 0.5, 0.55);
    UI.drawPositionedText(pauseGraphics2D, "(ESCAPE to exit)", font, 0.5, 0.6);
  }

  protected String getTitle() {
    return title;
  }
}
