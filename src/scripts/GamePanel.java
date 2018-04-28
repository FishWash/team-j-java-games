package scripts;

import scripts.gameWorlds.GameWorld;
import scripts.gameWorlds.TankBattleWorld;
import scripts.gameWorlds.TitleWorld;
import scripts.utility.Clock;
import scripts.utility.ClockListener;
import scripts.utility.KeyInputHandler;
import scripts.utility.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

// GamePanel runs on a thread and updates its display every frame.
public class GamePanel extends JPanel implements KeyListener, ClockListener
{
  public enum SpaceFunction {None, Start, Pause}
  public enum EscapeFunction {}
  public enum World {Title, TankBattle}

  private static GamePanel instance;
  private SpaceFunction spaceFunction;
  private BufferedImage pauseImage;

  private int spaceKeyCode = KeyEvent.VK_SPACE, escapeKeyCode = KeyEvent.VK_ESCAPE;

  public GamePanel(Dimension dimension) {
    instance = this;
    KeyInputHandler keyInputHandler = new KeyInputHandler();
    this.addKeyListener(keyInputHandler);
    this.addKeyListener(this);

    Clock clock = new Clock();
    Thread clockThread = new Thread(clock);
    clock.addClockListener(this);

    new TitleWorld();

    super.setSize(dimension);
    drawPauseImage();
    this.setFocusable(true);
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
      GameWorld.getInstance().displayOnGraphics(graphics);
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

  public void keyPressed(KeyEvent keyEvent) {
    int keyCode = keyEvent.getExtendedKeyCode();
    if (keyCode == spaceKeyCode) {
      switch (spaceFunction) {
        case Pause:
          pause();
          break;
        case Start:
          start(World.TankBattle);
          break;
      }
    }
  }
  public void keyReleased(KeyEvent keyEvent) {}
  public void keyTyped(KeyEvent keyEvent) {}

  private void start(World world) {

    GameWorld.getInstance().stopSounds();
    Clock.getInstance().stop();
    Clock clock = new Clock();
    Thread clockThread = new Thread(clock);
    clock.addClockListener(this);

    switch (world) {
      case TankBattle:
        new TankBattleWorld();
        break;
      case Title:
        new TitleWorld();
        break;
    }

    clockThread.start();
  }

  private void pause() {
    Clock clock = Clock.getInstance();
    if (clock.getPaused()) {
      clock.setPaused(false);
    }
    else {
      clock.setPaused(true);
      repaint();
    }
  }

  private void drawPauseImage() {
    pauseImage = new BufferedImage((int)super.getSize().getWidth(), (int)super.getSize().getHeight(),
                                   BufferedImage.TYPE_INT_ARGB);
    Graphics2D pauseGraphics2D = (Graphics2D) pauseImage.getGraphics();
    Font font = new Font("Impact", Font.PLAIN, 64);
    UI.drawPositionedTextImage( pauseGraphics2D, "Paused", Color.WHITE, font,
                               (int)super.getSize().getWidth(), (int)super.getSize().getHeight(),
                               0.5, 0.5);
  }
}
