package tankGame;

import javax.swing.*;
import java.awt.*;

public class TankGameApplication
{
  public static JFrame gameFrame;
  public static final Dimension windowDimension = new Dimension(800, 600);

  public static void main (String[] args)
  {
    gameFrame = new JFrame("TANKS FOR PLAYING OUR GAME");
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setSize(windowDimension);
    gameFrame.setUndecorated(true);
    gameFrame.setResizable(false);

    TankGamePanel tankGamePanel = new TankGamePanel(windowDimension);
    gameFrame.getContentPane().add(tankGamePanel, BorderLayout.CENTER);

    gameFrame.setSize(windowDimension);
    gameFrame.setVisible(true);
  }

  public static JFrame getGameFrame() {
    return gameFrame;
  }
}
