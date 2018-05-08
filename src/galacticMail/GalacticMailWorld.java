package galacticMail;

import galacticMail.gameObjects.*;
import general.GamePanel;
import general.GameWorld;
import general.gameObjects.GameObject;
import utility.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GalacticMailWorld extends GameWorld {
  public enum GameState{None, Victory, Defeat}
  private boolean gameOver;

  private Rocket rocket = null;
  private CopyOnWriteArrayList<Asteroid> asteroids
          = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Moon> moons = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Planet> planets = new CopyOnWriteArrayList<>();

  private List<CopyOnWriteArrayList<SpaceObject>> renderingLayers
          = new ArrayList<>();
  protected ArrayList<String> scoreboard = new ArrayList<>(5);

  private BufferedImage backgroundImage;

  protected GameState gameState = GameState.None;

  public GalacticMailWorld(int level) {
    instance = this;
    spritePath = "/galacticMail/sprites/";
    soundPath = "/galacticMail/sounds/";
    dimension = new Dimension(800, 600);
    new FlashingText();
    for (int i=0; i<5; i++) {
      renderingLayers.add(new CopyOnWriteArrayList<>());
    }
    this.level = level;
    initialize();
  }

  protected abstract void initialize();

  public boolean isGameOver() {
    return gameOver;
  }

  public CopyOnWriteArrayList<Asteroid> getAsteroids() {
    return asteroids;
  }

  public CopyOnWriteArrayList<Moon> getMoons() {
    return moons;
  }

  public CopyOnWriteArrayList<Planet> getPlanets() {
    return planets;
  }

  public void setGameState(GameState gamestate){
    if (!gameOver) {
      this.gameState = gamestate;
      gameOver = true;

      GalacticMailPanel galacticMailPanel = (GalacticMailPanel) GamePanel.getInstance();
      switch (gamestate) {
        case Victory:
          galacticMailPanel.setSpaceFunction(GamePanel.SpaceFunction.Start);
          break;
        case Defeat:
          Scoreboard.checkNewScore(PointsHandler.getInstance().getPoints());
          galacticMailPanel.setSpaceFunction(GamePanel.SpaceFunction.Restart);
          galacticMailPanel.stopLoopingSound();
          break;
      }
    }
  }

  protected void drawBackground(BufferedImage newBackgroundImage){
    double backgroundWidth = dimension.getWidth();
    double backgroundHeight = dimension.getHeight();
    double xScale = backgroundWidth / newBackgroundImage.getWidth();
    double yScale = backgroundHeight / newBackgroundImage.getHeight();
    BufferedImage newScaledBackgroundImage = UI.getScaledImage(newBackgroundImage, xScale, yScale, (int)backgroundWidth, (int)backgroundHeight);
    this.backgroundImage = newScaledBackgroundImage;
  }

  @Override
  public void display(Graphics graphics) {
    graphics.drawImage(getCurrentImage(), 0, 0, null);
  }

  @Override
  public GameObject instantiate(GameObject gameObject) {
    if (gameObject instanceof Rocket) {
      rocket = (Rocket) gameObject;
    }
    else if (gameObject instanceof Moon) {
      moons.add((Moon)gameObject);
    }
    else if (gameObject instanceof Asteroid) {
      asteroids.add((Asteroid)gameObject);
    }
    else if (gameObject instanceof Planet) {
      planets.add((Planet)gameObject);
    }

    if (gameObject instanceof ClockListener) {
      Clock.getInstance().addClockListener((ClockListener)gameObject);
    }

    if (gameObject instanceof SpaceObject) {
      SpaceObject spaceObject = (SpaceObject)gameObject;
      int renderingLayerIndex = spaceObject.getRenderingLayerIndex();
      renderingLayers.get(renderingLayerIndex).add(spaceObject);
    }

    return gameObject;
  }

  @Override
  public void destroy(GameObject gameObject) {
    if (gameObject instanceof Rocket) {
      if (rocket.equals(gameObject)) {
        rocket = null;
      }
    }
    else if (gameObject instanceof Moon) {
      moons.remove(gameObject);
    }
    else if (gameObject instanceof Asteroid) {
      asteroids.remove(gameObject);
    }
    else if (gameObject instanceof Planet) {
      planets.add((Planet)gameObject);
    }

    if (gameObject instanceof ClockListener) {
      Clock.getInstance().removeClockListener((ClockListener)gameObject);
    }

    if (gameObject instanceof SpaceObject) {
      SpaceObject spaceObject = (SpaceObject)gameObject;
      int renderingLayerIndex = spaceObject.getRenderingLayerIndex();
      renderingLayers.get(renderingLayerIndex).remove(spaceObject);
    }

  }

  @Override
  protected BufferedImage getCurrentImage() {
    BufferedImage currentImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    Graphics currentImageGraphics = currentImage.createGraphics();
    currentImageGraphics.drawImage(backgroundImage, 0, 0, null);

    for (CopyOnWriteArrayList<SpaceObject> renderingLayer : renderingLayers) {
      for (SpaceObject spaceObject : renderingLayer) {
        spaceObject.drawSprite(currentImageGraphics);
      }
    }

    return currentImage;
  }
}
