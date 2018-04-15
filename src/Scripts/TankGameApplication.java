package Scripts;

import Scripts.GamePanel;

import javax.swing.*;
import java.awt.*;

public class TankGameApplication
{
  static final Dimension windowDimension = new Dimension(800, 600);

  public static void main (String[] args)
  {
    JFrame frame = new JFrame("TANKS FOR PLAYING OUR GAME");
    GamePanel panel = new GamePanel(windowDimension);

    frame.setResizable(false);
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(windowDimension);
    panel.setSize(windowDimension);
    panel.initialize();
    frame.setVisible(true);
  }
}
