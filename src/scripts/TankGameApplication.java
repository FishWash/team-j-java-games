package scripts;

import javax.swing.*;
import java.awt.*;

public class TankGameApplication
{
  public static final Dimension windowDimension = new Dimension(800, 600);

  public static void main (String[] args)
  {
    JFrame gameFrame = new JFrame("TANKS FOR PLAYING OUR GAME");
    gameFrame.setSize(windowDimension);
    gameFrame.setResizable(false);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setUndecorated(true);

    GamePanel gamePanel = new GamePanel(windowDimension);
    gameFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);

    //gameFrame.pack(); //idk what pack is for

    gameFrame.setSize(windowDimension);
    gameFrame.setVisible(true);
  }
}
