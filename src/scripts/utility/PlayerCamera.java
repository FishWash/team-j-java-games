package scripts.utility;

import scripts.*;
import scripts.gameObjects.GameObject;
import scripts.gameObjects.Tank;
import scripts.gameWorlds.GameWorld;
import scripts.gameWorlds.TankBattleWorld;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerCamera extends GameObject {
  protected Vector2 position = new Vector2(0, 0);
  protected GameWorld.Player owner;
  protected GameObject playerToFollow;
  private Timer searchTimer = new Timer();
  private int searchDelay = 16;
  public enum DisplayText {None, Win, Lose, Draw};
  private DisplayText displayText = DisplayText.None;

  public PlayerCamera() {
    super();
  }
  public PlayerCamera(GameWorld.Player owner) {
    super();
    this.owner = owner;
  }

  public BufferedImage getPlayerDisplay(BufferedImage currentImage, int screenWidth, int screenHeight) {
    int gameWidth = currentImage.getWidth();
    int gameHeight = currentImage.getHeight();
    int margin = 50;
    int healthBarHeight = 25;
    int healthBarWidth = screenWidth - margin * 2;

    if (playerToFollow != null && playerToFollow.getAlive()) {
      position = playerToFollow.getPosition();
    }
    else {
      // search for player with same owner
      if (searchTimer.isDone()) {
        CopyOnWriteArrayList<Tank> players = GameWorld.getInstance().getTanks();
        for (GameObject player : players) {
          if (player.getOwner() == this.owner) {
            playerToFollow = player;
            break;
          }
        }
        searchTimer.set(searchDelay);
      }
    }

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
      int green = (int) ( 255*(Math.min(1, 1.5*healthProportion)) );
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

    addLives(graphics2D, margin + fontMetrics.stringWidth(playerString) + 10, margin - fontMetrics.getHeight(), (int)((fontMetrics.getHeight() + 3) * 0.95));

    font = new Font("Impact", Font.BOLD, 50);
    graphics2D.setFont(font);
    fontMetrics = graphics2D.getFontMetrics(font);
    if(displayText == DisplayText.Win){
      graphics2D.drawString("You Win!", (screenWidth / 2) - fontMetrics.stringWidth("You Win!") / 2, screenHeight / 2);
    } else if (displayText == DisplayText.Lose){
      graphics2D.drawString("You Lose.", (screenWidth / 2) - fontMetrics.stringWidth("You Win!") / 2, screenHeight / 2);
    } else if(displayText == DisplayText.Draw){
      graphics2D.setColor(Color.WHITE);
      graphics2D.drawString("Draw",(screenWidth / 2) - fontMetrics.stringWidth("Draw") / 2, screenHeight / 2);
    }

//    font = new Font("Impact", Font.BOLD, 25);
//    graphics2D.setFont(font);
//    fontMetrics = graphics2D.getFontMetrics(font);

//    graphics2D.drawString("Press space to restart", screenWidth / 2, screenHeight / 2);

    return playerDisplay;
  }

  private void addLives(Graphics currentPlayerImage, int xPos, int yPos, int size){
    GameWorld gameWorld = GameWorld.getInstance();
    int lives = 0;
    int spriteGap = 2;
    BufferedImage lifeSprite;
    if (playerToFollow instanceof Tank) {
      lifeSprite = UI.getScaledImage(((Tank) playerToFollow).getMultiSprite().getSubSpriteByRotation(90), size /(double)playerToFollow.getSprite().getHeight(), size, size);
      //lifeSprite = ((Tank) playerToFollow).getMultiSprite().getSubSpriteByRotation(90);

      int lifeSpriteWidth = lifeSprite.getWidth();

      if (gameWorld instanceof TankBattleWorld) {
        lives = ((TankBattleWorld) gameWorld).getLives(owner);
      }
      for (int i = lives; i > 0; i--) {
        currentPlayerImage.drawImage(lifeSprite, xPos, yPos, null);
        xPos += lifeSpriteWidth + spriteGap;
      }
    }
  }

  public void setDisplayText(DisplayText displayText){
    this.displayText = displayText;
  }
}
