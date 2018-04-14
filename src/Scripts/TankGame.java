package Scripts;

import Scripts.GameWorld;

import javax.swing.*;
import java.awt.*;

public class TankGame
{
  public static void main (String[] args)
  {
    JFrame frame = new JFrame("TANKS FOR PLAYING OUR GAME");
    GameWorld gameWorld = new GameWorld();

    frame.getContentPane().add(gameWorld, BorderLayout.CENTER);
    frame.setSize(800, 600);
    gameWorld.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    try
    {
      gameWorld.initialize();
    } catch (Exception e) {
      System.out.println("it no works");
  }
    frame.setVisible(true);
  }
}
