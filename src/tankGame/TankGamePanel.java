package tankGame;

import general.GamePanel;
import utility.Clock;

import java.awt.*;

public class TankGamePanel extends GamePanel {

  public TankGamePanel(Dimension dimension) {
    super(dimension);
    new TitleWorld();
    title = "Tanks for playing our game!";
  }

  @Override
  protected void start() {
    TankGameWorld.getInstance().stopSounds();
    Clock.getInstance().stop();
    Clock clock = new Clock();
    Thread clockThread = new Thread(clock);
    clock.addClockListener(this);

    new TankDeathmatch();

    clockThread.start();
  }
}
