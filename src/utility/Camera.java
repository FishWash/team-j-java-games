package utility;

import galacticMail.PointsHandler;
import tankGame.TankGameWorld;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

  public static void displayLevel(Graphics2D graphics2D, int levelNum){
    Font font = new Font("Impact", Font.PLAIN, 32);
    String level = "Level " + Integer.toString(levelNum);
    UI.drawPositionedText(graphics2D, level, font, 0.5, 0.95);
  }

  public static void displayPoints(Graphics2D graphics2D, int points){
    Font font = new Font("Impact", Font.PLAIN, 32);
    UI.drawPositionedText(graphics2D, "$" + Integer.toString(points), font, 0.5, 0.05);
  }

  public static void displayLoseScreen(Graphics2D graphics2D){
    ArrayList<String> leaderboard = Scoreboard.readScoreboard("src/galacticMail/Scoreboard.txt");
    Font font = new Font("Impact", Font.PLAIN, 64);
    UI.drawPositionedText(graphics2D, "DELIVERY FAILED", font, 0.5, 0.20);
    font = new Font("Impact", Font.BOLD, 32);
    UI.drawPositionedText(graphics2D, "SCOREBOARD", font, 0.5, 0.35);
    double heightProportion = 0.42;

    font = new Font("Impact", Font.PLAIN, 25);
    for (int i = 0; i < leaderboard.size(); i++){
      if(leaderboard.get(i).equals(Integer.toString(PointsHandler.getInstance().getPoints()))){
        graphics2D.setColor(Color.YELLOW);
      } else {
        graphics2D.setColor(Color.WHITE);
      }
      UI.drawPositionedText(graphics2D, "$" + leaderboard.get(i), font,
                            0.5, heightProportion);
      heightProportion += 0.05;
    }
  }
  public static void displayWinScreen(Graphics2D graphics2D) {
    Font font = new Font("Impact", Font.PLAIN, 64);
    UI.drawPositionedText(graphics2D, "DELIVERY COMPLETED", font, 0.5, 0.4);
  }
}
