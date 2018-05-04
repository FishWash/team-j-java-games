package galacticMail;

import general.GameApplication;

import java.awt.*;

public class GalacticMailApplication extends GameApplication {

  public static void main(String[] args) {
    GameApplication.windowDimension = new Dimension(800, 600);
    new GalacticMailPanel(windowDimension);
    GameApplication.main(args);
  }
}
