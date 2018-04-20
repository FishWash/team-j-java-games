package Scripts;

import Scripts.GameWorld;

import javax.swing.*;
import java.awt.*;

public class TankGameApplication
{
  static final Dimension gameDimension = new Dimension(1000, 1000);
  static final Dimension windowDimension = new Dimension(800, 600);

  public static void main (String[] args)
  {
    JFrame gameFrame = new JFrame("TANKS FOR PLAYING OUR GAME");
    gameFrame.setResizable(false);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setUndecorated(true);

    GameWorld gameWorld = new GameWorld(gameDimension);
    GamePanel gamePanel = new GamePanel(windowDimension, gameWorld);


    gameFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);
    //gameFrame.pack();
    gameFrame.setSize(windowDimension);

    gameFrame.setVisible(true);
  }
}
