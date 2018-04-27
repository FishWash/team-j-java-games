package scripts;

import scripts.gameObjects.TankSpawner;
import scripts.utility.Camera;
import scripts.utility.Vector2;

import java.awt.*;

public class TitleWorld extends GameWorld {

  private Camera mainCamera;

  public void displayOnGraphics(Graphics graphics) {

  }

  protected void initialize() {
    collisionHandler.readMapFile("TitleMap.txt", TILE_SIZE);
    drawBackgroundImage("TitleMap.txt", loadSprite("background_tile.png"),
                        loadSprite("wall_indestructible2.png"));
    instantiate( new TankSpawner(new Vector2(128, 256), Player.One) );
    instantiate( new TankSpawner(new Vector2(512, 256), Player.Two) );
  }
}
