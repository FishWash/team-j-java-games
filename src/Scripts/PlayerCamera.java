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
    graphics2D.setColor(Color.red);
    graphics2D.fillRect(margin , margin, healthBarWidth, healthBarHeight);
    graphics2D.setColor(Color.GREEN);

    if(objectToFollow instanceof Damageable){
      int health = ((Damageable) objectToFollow).getHealth();
      int maxHealth = ((Damageable) objectToFollow).getMaxHealth();
      healthProportion = (double)health / (double)maxHealth;
      graphics2D.fillRect(margin, margin, (int)(healthBarWidth * healthProportion), healthBarHeight);
    }

    return playerDisplay;
  }
}
