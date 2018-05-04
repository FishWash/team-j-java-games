package general;

import javax.swing.*;
import java.awt.*;

public abstract class GameApplication
{
  private static JFrame gameFrame;
  protected static Dimension windowDimension;

  protected static void main (String[] args)
  {
    gameFrame = new JFrame(GamePanel.getInstance().getTitle());
    gameFrame.setSize(windowDimension);
    gameFrame.setResizable(false);
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setUndecorated(true);

    gameFrame.getContentPane().add(GamePanel.getInstance(), BorderLayout.CENTER);

    gameFrame.setSize(windowDimension);
    gameFrame.setVisible(true);
  }


}
