package galacticMail;

import general.GamePanel;

import java.awt.*;

public class GalacticMailPanel extends GamePanel {

  public GalacticMailPanel(Dimension dimension) {
    super(dimension);
    new GalacticTitleWorld();
    title = "Tanks for playing our game!";
  }

  @Override
  protected void start() {

  }
}
