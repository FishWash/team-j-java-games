package galacticMail;

import general.GamePanel;

import java.awt.*;

public class GalacticMailPanel extends GamePanel {

  public GalacticMailPanel(Dimension dimension) {
    super(dimension);
    new LevelOne();
    title = "You rock-et!";
  }

  @Override
  protected void start() {

  }
}
