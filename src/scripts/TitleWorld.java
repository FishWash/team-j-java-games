package scripts;

import scripts.gameObjects.TankSpawner;

import scripts.utility.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleWorld extends GameWorld {

  protected void initialize() {
    dimension = new Dimension(800, 600);
    collisionHandler.readMapFile("TitleMap.txt", TILE_SIZE);
    drawBackgroundImage("TitleMap.txt", loadSprite("background_tile.png"),
                        loadSprite("wall_indestructible2.png"));
    instantiate( new TankSpawner(new Vector2(128, 256), Player.One) );
    instantiate( new TankSpawner(new Vector2(512, 256), Player.Two) );
  }

  public void displayOnGraphics(Graphics graphics) {
    BufferedImage currentImage = getCurrentImage();

    try {
      graphics.drawImage(currentImage, 0, 0, null);
    } catch (Exception e) {
      System.out.println("ERROR in TankBattleWorld: " + e);
    }
  }
}
