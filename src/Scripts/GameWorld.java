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

public class GameWorld extends JPanel
{
  private Dimension dimension;
  private BufferedImage bgTile;

  private ArrayList<Terrain> terrains;
  private ArrayList<Tank> tanks;
  private ArrayList<Bullet> bullets;

  private HashMap<String, Image> spriteMap;

  public GameWorld(Dimension dimension)
  {
    this.dimension = dimension;
  }

  public void initialize()
  {
    bgTile = loadSprite("/Sprites/background_tile.png");
  }

  private BufferedImage loadSprite(String filePath)
  {
    BufferedImage _bImg = null;
    try {
      _bImg = ImageIO.read(getClass().getResourceAsStream(filePath));
    } catch (Exception e) {
      System.out.println("Sprite not found at " + filePath);
    }

    return _bImg;
  }

  @Override
  public void paintComponent(Graphics graphics)
  {
    super.paintComponent(graphics);
    paintBackground(graphics);
  }

  private void paintBackground(Graphics graphics)
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
