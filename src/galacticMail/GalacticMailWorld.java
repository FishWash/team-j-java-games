package galacticMail;

import galacticMail.gameObjects.Asteroid;
import galacticMail.gameObjects.Moon;
import galacticMail.gameObjects.Rocket;
import galacticMail.gameObjects.SpaceObject;
import general.GameWorld;
import general.gameObjects.GameObject;
import utility.Clock;
import utility.ClockListener;
import utility.UI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GalacticMailWorld extends GameWorld {
  public enum Team {Player, Enemy}

  private Rocket rocket = null;
  private CopyOnWriteArrayList<Asteroid> asteroids
          = new CopyOnWriteArrayList<>();
  private CopyOnWriteArrayList<Moon> moons = new CopyOnWriteArrayList<>();

  private List<CopyOnWriteArrayList<SpaceObject>> renderingLayers
          = new ArrayList<>();

  private BufferedImage backgroundImage;

  public GalacticMailWorld() {
    instance = this;
    spritePath = "/galacticMail/sprites/";
    soundPath = "/galacticMail/sounds/";
    dimension = new Dimension(800, 600);
    for (int i=0; i<5; i++) {
      renderingLayers.add(new CopyOnWriteArrayList<>());
    }

    initialize();
  }

  protected abstract void initialize();

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

    if (gameObject instanceof ClockListener) {
      Clock.getInstance().addClockListener((ClockListener)gameObject);
    }

    if (gameObject instanceof SpaceObject) {
      SpaceObject spaceObject = (SpaceObject)gameObject;
      int renderingLayerIndex = spaceObject.getRenderingLayerIndex();
      renderingLayers.get(renderingLayerIndex).add(spaceObject);
    }

    drawBackground(loadSprite("Background.png"));

    return gameObject;
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

  public CopyOnWriteArrayList<Asteroid> getAsteroids() {
    return asteroids;
  }

  public CopyOnWriteArrayList<Moon> getMoons() {
    return moons;
  }
}
