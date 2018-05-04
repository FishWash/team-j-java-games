package scripts.gameWorlds;

import scripts.gameObjects.spaceObjects.Asteroid;
import scripts.gameObjects.GameObject;
import scripts.gameObjects.spaceObjects.Moon;
import scripts.gameObjects.spaceObjects.Rocket;
import scripts.gameObjects.spaceObjects.SpaceObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class GalacticMailWorld extends GameWorld {
  public enum Team {Player, Enemy}

  private Rocket rocket;
  private CopyOnWriteArrayList<Asteroid> asteroids;
  private CopyOnWriteArrayList<Moon> moons;

  private List<CopyOnWriteArrayList<SpaceObject>> renderingLayers
          = new ArrayList<>();

  public GalacticMailWorld() {
    for (int i=0; i<5; i++) {
      renderingLayers.add(new CopyOnWriteArrayList<>());
    }
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

    if (gameObject instanceof SpaceObject) {
      SpaceObject spaceObject = (SpaceObject)gameObject;
      int renderingLayerIndex = spaceObject.getRenderingLayerIndex();
      renderingLayers.get(renderingLayerIndex).remove(spaceObject);
    }

  }

  @Override
  protected BufferedImage getCurrentImage() {
    return null;
  }
}
