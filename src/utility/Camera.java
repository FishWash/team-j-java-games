package utility;

import galacticMail.GalacticMailWorld;
import general.GameWorld;
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
    UI.drawPositionedTextImage(graphics2D, "$" + Integer.toString(points), Color.WHITE, font, GameWorld.getInstance().getDimension().getWidth(),
                               GalacticMailWorld.getInstance().getDimension().getHeight(), 0.5, 0.02);
  }

  public static void displayloseScreen(Graphics2D graphics2D){
    Font font = new Font("Impact", Font.PLAIN, 100);
    UI.drawPositionedTextImage(graphics2D, "DELIVERY FAILED", Color.WHITE, font, GameWorld.getInstance().getDimension().getWidth(),
            GalacticMailWorld.getInstance().getDimension().getHeight(), 0.5, 0.5);
  }
  public static void displayWinScreen(Graphics2D graphics2D){
    Font font = new Font("Impact", Font.PLAIN, 100);
    UI.drawPositionedTextImage(graphics2D, "DELIVERY COMPLETED", Color.WHITE, font, GameWorld.getInstance().getDimension().getWidth(),
            GalacticMailWorld.getInstance().getDimension().getHeight(), 0.5, 0.5);
  }
}
