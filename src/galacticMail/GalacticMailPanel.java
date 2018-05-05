package galacticMail;

import general.GamePanel;
import general.GameWorld;

import java.awt.*;

public class GalacticMailPanel extends GamePanel {

  public GalacticMailPanel(Dimension dimension) {
    super(dimension);
    new GalacticTitleWorld();
    title = "You rock-et!";
  }

  @Override
  protected void start() {
    resetClock();
    int newLevel = GameWorld.getInstance().getLevel()+1;
    System.out.println(newLevel);
    instantiateGameWorld(new GalacticLevel(newLevel));
  }

  @Override
  protected void restart() {
    resetClock();
    instantiateGameWorld(new GalacticTitleWorld());
  }
}
