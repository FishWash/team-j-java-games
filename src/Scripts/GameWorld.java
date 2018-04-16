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

public class GameWorld
{
  private Dimension dimension;
  private BufferedImage backgroundImage; //image of GameWorld with background tiles and walls

  private ArrayList<Terrain> terrains;
  private ArrayList<Tank> tanks;
  private ArrayList<Bullet> bullets;

  private HashMap<String, Image> spriteMap;

  public GameWorld(Dimension dimension)
  {
    this.dimension = dimension;
    initialize();
  }

  public void initialize()
  {
    drawBackgroundImage();
  }

  public synchronized BufferedImage getCurrentImage()
  {
    BufferedImage _currentImage = backgroundImage;
    Graphics _currentImageGraphics = _currentImage.getGraphics();

    // draw other stuff. tanks, bullets, destructible walls, etc

    return _currentImage;
  }

  private BufferedImage loadSprite(String fileName)
  {
    BufferedImage _bImg = null;
    try {
      _bImg = ImageIO.read(getClass().getResourceAsStream("/Sprites/" + fileName));
    } catch (Exception e) {
      System.out.println(fileName + " not found");
    }

    return _bImg;
  }

  private void drawBackgroundImage()
  {
    BufferedImage _bgTile = loadSprite("background_tile.png");
    backgroundImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics _graphics = backgroundImage.createGraphics();

    for(int i=0; i<dimension.width; i += _bgTile.getWidth()) {
      for (int j=0; j<dimension.height; j += _bgTile.getHeight()) {
        _graphics.drawImage(_bgTile, i, j, null);
      }
    }
  }
}
