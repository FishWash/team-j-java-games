package utility;

import galacticMail.GalacticMailWorld;
import general.GamePanel;
import tankGame.TankGameWorld;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Camera {
  protected Vector2 position;

  public Camera(){
    this.position = new Vector2(0,0);
  }

  public static BufferedImage getMinimapDisplay(BufferedImage currentImage){
    double scale = 0.2;
    int minimapWidth = (int) (TankGameWorld.getInstance().getDimension().width * scale);
    int minimapHeight = (int) (TankGameWorld.getInstance().getDimension().width * scale);
    BufferedImage resizedMap = UI.getScaledImage(currentImage, scale, scale, minimapWidth, minimapHeight);

    return resizedMap;
  }

  public static void displayPoints(Graphics2D graphics2D, int points){
    Font font = new Font("Impact", Font.PLAIN, 25);
    UI.drawPositionedTextImage(graphics2D, Integer.toString(points), Color.WHITE, font, GamePanel.getInstance().getWidth(),
                               GamePanel.getInstance().getHeight(), 0.5, 0.97);
  }

  public static void displayLevel(Graphics2D graphics2D, int levelNum){
    Font font = new Font("Impact", Font.PLAIN, 25);
    String level = "level " + Integer.toString(levelNum);
    UI.drawPositionedTextImage(graphics2D, level, Color.WHITE, font, GamePanel.getInstance().getWidth(),
                               GamePanel.getInstance().getHeight(), 0.5, 0.02);
  }

  public static void displayLoseScreen(Graphics2D graphics2D){
    Font font = new Font("Impact", Font.PLAIN, 50);
    UI.drawPositionedTextImage(graphics2D, "YOU LOSE", Color.WHITE, font, GalacticMailWorld.getInstance().getDimension().getWidth(),
            GalacticMailWorld.getInstance().getDimension().getHeight(), 0.5, 0.4);
  }
  public static void displayWinScreen(Graphics2D graphics2D){
    Font font = new Font("Impact", Font.PLAIN, 50);
    UI.drawPositionedTextImage(graphics2D, "YOU WIN", Color.WHITE, font, GalacticMailWorld.getInstance().getDimension().getWidth(),
            GalacticMailWorld.getInstance().getDimension().getHeight(), 0.5, 0.4);
  }
}
