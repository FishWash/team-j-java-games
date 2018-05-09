package tankGame;

import general.gameObjects.Damageable;
import tankGame.gameObjects.Tank;
import tankGame.gameObjects.TankGameObject;
import tankGame.weapons.Weapon;
import utility.Timer;
import utility.UI;
import utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerCamera extends TankGameObject {
  protected Vector2 position = new Vector2(0, 0);
  protected TankGameWorld.Player owner;
  protected TankGameObject playerToFollow;
  private Timer searchTimer = new Timer();
  private int searchDelay = 16;
  public enum DisplayText {None, Win, Lose, Draw}
  private DisplayText displayText = DisplayText.None;

  public PlayerCamera(TankGameWorld.Player owner) {
    super();
    this.owner = owner;
  }

  public BufferedImage getPlayerDisplay(BufferedImage currentImage, int screenWidth, int screenHeight) {
    int gameWidth = currentImage.getWidth();
    int gameHeight = currentImage.getHeight();
    int margin = 50;
    int healthBarHeight = 25;
    int healthBarWidth = screenWidth - margin * 2;

    if (playerToFollow != null && playerToFollow.isAlive()) {
      position = playerToFollow.getPosition();
    }
    else {
      // search for player with same owner
      if (searchTimer.isDone()) {
        CopyOnWriteArrayList<Tank> players = TankGameWorld.getInstance().getTanks();
        for (TankGameObject player : players)
          if (player.getOwner() == this.owner) {
            playerToFollow = player;
            break;
          }
        searchTimer.set(searchDelay);
      }
    }

    int displayX = (int) Math.max(0, Math.min(gameWidth - screenWidth, position.x - screenWidth / 2));
    int displayY = (int) Math.max(0, Math.min(gameHeight - screenHeight, position.y - screenHeight / 2));

    // Get Player's slice of current game image
    BufferedImage subImage = currentImage.getSubimage(displayX, displayY, screenWidth, screenHeight);
    BufferedImage playerDisplay = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics2D = playerDisplay.createGraphics();
    graphics2D.drawImage(subImage, 0, 0, null);
//    graphics2D.setColor(new Color(191, 50, 50));
    graphics2D.setColor(new Color(128,0,0,128));
    graphics2D.fillRect(margin , margin, healthBarWidth, healthBarHeight);

    // display healthbars
    if(playerToFollow instanceof Damageable){
      int health = ((Damageable) playerToFollow).getHealth();
      int maxHealth = ((Damageable) playerToFollow).getMaxHealth();
      Double healthProportion = (double)health / (double)maxHealth;

      int red = (int) ( 255*(Math.min(1, 3 - 3*healthProportion)) );
      int green = (int) ( 255*(Math.min(1, 1.5*healthProportion)) );
      graphics2D.setColor(new Color(red, green, 32));
      graphics2D.fillRect(margin, margin, (int)(healthBarWidth * healthProportion), healthBarHeight);
    }

    graphics2D.setColor(Color.BLACK);
    graphics2D.setStroke(new BasicStroke(2));
    graphics2D.drawRect(margin, margin, healthBarWidth, healthBarHeight);

    // Display player title
    String playerString;
    if (playerToFollow.getOwner() == TankGameWorld.Player.One) {
      playerString = "Player 1";
      graphics2D.setColor(new Color(0, 0, 255, 128));
    }
    else if (playerToFollow.getOwner() == TankGameWorld.Player.Two) {
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

    // display win/lose text
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

    addAmmoCount(graphics2D);

    return playerDisplay;
  }

  private void addLives(Graphics currentPlayerImage, int xPos, int yPos, int size){
    TankGameWorld tankGameWorld = TankGameWorld.getInstance();
    int lives = 0;
    int spriteGap = 2;
    BufferedImage lifeSprite;
    if (playerToFollow instanceof Tank) {
      lifeSprite = UI.getScaledImage(((Tank) playerToFollow).getMultiSprite().getSubSpriteByRotation(90), size / (double)playerToFollow.getSprite().getWidth(),size /(double)playerToFollow.getSprite().getHeight(), size, size);
      //lifeSprite = ((Tank) playerToFollow).getMultiSprite().getSubSpriteByRotation(90);

      int lifeSpriteWidth = lifeSprite.getWidth();

      if (tankGameWorld instanceof TankDeathmatch) {
        lives = ((TankDeathmatch) tankGameWorld).getLives(owner);
      }
      for (int i = lives; i > 0; i--) {
        currentPlayerImage.drawImage(lifeSprite, xPos, yPos, null);
        xPos += lifeSpriteWidth + spriteGap;
      }
    }
  }

  private void addAmmoCount(Graphics2D graphics){
    if(playerToFollow instanceof Tank){
      Weapon weapon = ((Tank) playerToFollow).getWeapon();
      int ammo = weapon.getAmmo();
      graphics.setColor(Color.WHITE);
      Font font = new Font("Impact", Font.BOLD, 30);

      if(ammo >= 0){
        UI.drawPositionedText(graphics, weapon.getWeaponName(), font, 0.25, 0.9);
        UI.drawPositionedText(graphics, Integer.toString(ammo), font, 0.25, 0.95);
      }
    }
  }

  public void setDisplayText(DisplayText displayText){
    this.displayText = displayText;
  }
}
