package scripts.utility;

import scripts.GameWorld;
import scripts.gameObjects.Damageable;
import scripts.gameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerCamera {
  protected Vector2 position;
  protected GameObject playerToFollow;

  public PlayerCamera(){
    position = new Vector2(0,0);
  }
  public PlayerCamera(GameObject gameObject){
    position = new Vector2(0,0);
    playerToFollow = gameObject;
  }


  public BufferedImage getPlayerDisplay(BufferedImage currentImage, int screenWidth, int screenHeight) {
    int gameWidth = currentImage.getWidth();
    int gameHeight = currentImage.getHeight();
    int margin = 50;
    int healthBarHeight = 25;
    int healthBarWidth = screenWidth - margin * 2;
    this.position = playerToFollow.getPosition();

    int displayX = (int) Math.max(0, Math.min(gameWidth - screenWidth, position.x - screenWidth / 2));
    int displayY = (int) Math.max(0, Math.min(gameHeight - screenHeight, position.y - screenHeight / 2));

    BufferedImage subImage = currentImage.getSubimage(displayX, displayY, screenWidth, screenHeight);
    BufferedImage playerDisplay = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = playerDisplay.createGraphics();
    graphics2D.drawImage(subImage, 0, 0, null);
//    graphics2D.setColor(new Color(191, 50, 50));
    graphics2D.setColor(new Color(128,0,0,128));
    graphics2D.fillRect(margin , margin, healthBarWidth, healthBarHeight);

    if(playerToFollow instanceof Damageable){
      int health = ((Damageable) playerToFollow).getHealth();
      int maxHealth = ((Damageable) playerToFollow).getMaxHealth();
      Double healthProportion = (double)health / (double)maxHealth;

      int red = (int) ( 255*(Math.min(1, 3 - 3*healthProportion)) );
      int green = (int) ( 255*(Math.min(1, healthProportion)) );
//      System.out.println("(PlayerCamera) Red: " + red + " Green: " + green);
      graphics2D.setColor(new Color(red, green, 32));
      graphics2D.fillRect(margin, margin, (int)(healthBarWidth * healthProportion), healthBarHeight);
    }

    graphics2D.setColor(Color.BLACK);
    graphics2D.setStroke(new BasicStroke(2));
    graphics2D.drawRect(margin, margin, healthBarWidth, healthBarHeight);

    String playerString;
    if (playerToFollow.getOwner() == GameWorld.Player.One) {
      playerString = "Player 1";
      graphics2D.setColor(new Color(0, 0, 255, 128));
    }
    else if (playerToFollow.getOwner() == GameWorld.Player.Two) {
      playerString = "Player 2";
      graphics2D.setColor(new Color(255, 0, 0, 128));
    }
    else {
      playerString = "друг";
      graphics2D.setColor(new Color(128, 128, 128, 128));
    }

    Font font = new Font("Impact", Font.BOLD, 30);
    graphics2D.setFont(font);
    FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

    graphics2D.fillRect(margin, margin - fontMetrics.getHeight()+3, fontMetrics.stringWidth(playerString)+8,
                        fontMetrics.getHeight()-3);
    int yPos = margin - ((margin - fontMetrics.getHeight()) / 2);
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setColor(Color.WHITE);
    graphics2D.drawString(playerString, margin+4, yPos);



    return playerDisplay;
  }
}
