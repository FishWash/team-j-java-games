package Scripts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GameWorld extends JPanel
{


  private BufferedImage bgImage;

  private ArrayList<Terrain> terrains;
  private ArrayList<Tank> tanks;
  private ArrayList<Bullet> bullets;

  private HashMap<String, Image> spriteMap;

  public GameWorld()
  {

  }

  public void initialize() throws Exception
  {
//    setBackground(Color.black);

    bgImage = ImageIO.read(getClass().getResourceAsStream("/Sprites/background_tile.png"));
//    Graphics g = backgroundImage.createGraphics();
//    super.paintComponent(g);
//    g.drawImage(backgroundImage, 0, 0, this);

//    JLabel picLabel = new JLabel(new ImageIcon(backgroundImage));
//    add(picLabel);

    Graphics g = bgImage.createGraphics();
    super.paintComponent(g);
    g.drawImage(bgImage, 0, 0, this);
  }
}
