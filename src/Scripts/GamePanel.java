package Scripts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GamePanel extends JPanel
{
  Dimension dimension;
  BufferedImage bgTile;

  private ArrayList<Terrain> terrains;
  private ArrayList<Tank> tanks;
  private ArrayList<Bullet> bullets;

  private HashMap<String, Image> spriteMap;

  public GamePanel(Dimension dimension)
  {
    this.dimension = dimension;
  }

  public void initialize()
  {
    try {
      bgTile = ImageIO.read(getClass().getResourceAsStream("/Sprites/background_tile.png"));
    } catch (Exception e) {
      System.out.println("Sprite not found");
    }
  }

  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    paintBackground(graphics);
  }

  void paintBackground(Graphics graphics)
  {
    if (bgTile != null)
    {
      for(int i=0; i<dimension.width; i += bgTile.getWidth())
      {
        for (int j=0; j<dimension.height; j+=bgTile.getHeight())
        {
          graphics.drawImage(bgTile, i, j, null);
        }
      }
    }
  }
}
