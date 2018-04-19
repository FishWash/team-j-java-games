package Scripts;

import Scripts.GameWorld;

import javax.swing.*;
import java.awt.*;

public class TankGameApplication
{
  private static final Dimension gameDimension = new Dimension(1000, 1000);
  private static final Dimension windowDimension = new Dimension(800, 600);

  public static void main (String[] args)
  {
    JFrame gameFrame = new JFrame("TANKS FOR PLAYING OUR GAME");

    gameFrame.setSize(windowDimension);
    gameFrame.setResizable(false);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GameWorld gameWorld = new GameWorld(gameDimension);
    GamePanel gamePanel = new GamePanel(windowDimension, gameWorld);

    gameFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);
    //gameFrame.pack(); //idk what pack is for
    gameFrame.setVisible(true);

    Thread thread = new Thread(gamePanel);
    thread.start();
  }
}
