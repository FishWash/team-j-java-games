package Scripts;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerCamera {
  protected Vector2 position;
  protected GameObject objectToFollow;

  public PlayerCamera(){
    position = new Vector2(0,0);
  }
  public PlayerCamera(GameObject gameObject){
    objectToFollow = gameObject;
  }


  public BufferedImage getPlayerDisplay(BufferedImage currentImage, int screenWidth, int screenHeight){
    int gameWidth = currentImage.getWidth();
    int gameHeight = currentImage.getHeight();
    int margin = 50;
    int healthBarHeight = 25;
    int healthBarWidth = screenWidth - margin * 2;
    double healthProportion;
    this.position = objectToFollow.position;

    int displayX = (int) Math.max(0, Math.min(gameWidth - screenWidth, position.x - screenWidth / 2));
    int displayY = (int) Math.max(0, Math.min(gameHeight - screenHeight, position.y - screenHeight / 2));

    BufferedImage subImage = currentImage.getSubimage(displayX, displayY, screenWidth, screenHeight);
    BufferedImage playerDisplay = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = playerDisplay.createGraphics();
    graphics2D.drawImage(subImage, 0, 0, null);
//    graphics2D.setColor(new Color(191, 50, 50));
    graphics2D.setColor(new Color(128,0,0,128));
    graphics2D.fillRect(margin , margin, healthBarWidth, healthBarHeight);

    if(objectToFollow instanceof Damageable){
      int health = ((Damageable) objectToFollow).getHealth();
      int maxHealth = ((Damageable) objectToFollow).getMaxHealth();
      healthProportion = (double)health / (double)maxHealth;
      graphics2D.setColor(new Color((int)(255 * (1-healthProportion)), (int)(191 * healthProportion), 0));
      graphics2D.fillRect(margin, margin, (int)(healthBarWidth * healthProportion), healthBarHeight);
    }

    graphics2D.setColor(Color.BLACK);
    graphics2D.setStroke(new BasicStroke(2));
    graphics2D.drawRect(margin, margin, healthBarWidth, healthBarHeight);

    String player;
    String playerNum;
    if(objectToFollow.getOwner().toString().equals("One")){
      playerNum = "1";
    }else{
      playerNum = "2";
    }

    player = "Player " + playerNum;

    if(player.equals("Player 1")){
      graphics2D.setColor(new Color(0, 0, 255, 128));
    }else{
      graphics2D.setColor(new Color(255, 0, 0, 128));
    }

    Font font = new Font("Impact", Font.BOLD, 30);
    graphics2D.setFont(font);
    FontMetrics fontMetrics = graphics2D.getFontMetrics(font);

    graphics2D.fillRect(margin, margin - fontMetrics.getHeight()+3, fontMetrics.stringWidth(player)+8, fontMetrics.getHeight()-3);
    int yPos = margin - ((margin - fontMetrics.getHeight()) / 2);
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setColor(Color.WHITE);
    graphics2D.drawString(player, margin+4, yPos);



    return playerDisplay;
  }
}
