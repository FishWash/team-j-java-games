package galacticMail;

import general.GamePanel;

import java.awt.*;

public class GalacticMailPanel extends GamePanel {

  public GalacticMailPanel(Dimension dimension) {
    super(dimension);
    new GalacticTitleWorld();
//    new GalacticLevelOne();
    title = "You rock-et!";
  }

  @Override
  protected void start() {

  }
}
