package galacticMail;

import general.GamePanel;

import java.awt.*;

public class GalacticMailPanel extends GamePanel {

  public GalacticMailPanel(Dimension dimension) {
    super(dimension);
    new LevelOne();
    title = "Tanks for playing our game!";
  }

  @Override
  protected void start() {

  }
}
