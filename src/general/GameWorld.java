package general;

import general.gameObjects.GameObject;
import utility.CollisionHandler;
import utility.SoundHandler;
import utility.SpriteHandler;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public abstract class GameWorld {
  public static GameWorld instance;
  protected Dimension dimension = new Dimension(0, 0);
  protected int level = 0;
  protected boolean gameOver = false;

  protected String spritePath = "sprites/";
  protected String soundPath = "sounds/";

  public static GameWorld getInstance() {
    return instance;
  }
  public Dimension getDimension() {
    return dimension;
  }
  public int getLevel() {
    return level;
  }
  public boolean isGameOver() {
    return gameOver;
  }
  protected abstract BufferedImage getCurrentImage();
  public String getSpritePath() {
    return spritePath;
  }
  public String getSoundPath() {
    return soundPath;
  }

  public abstract void display(Graphics graphics);

  public abstract GameObject instantiate(GameObject gameObject);

  public abstract void destroy(GameObject gameObject);
}
