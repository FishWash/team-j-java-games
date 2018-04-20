package Scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
  private GameWorld gameWorld;

  public GamePanel(Dimension dimension, GameWorld gameWorld) {
    super.setSize(dimension);
    this.gameWorld = gameWorld;
  }


  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    gameWorld.gameDisplay(graphics);
  }
}
