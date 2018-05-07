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
    resetClock();
    new TankDeathmatch();
    clockThread.start();
  }

  @Override
  protected void restart() {
    // no restart
  }
}
