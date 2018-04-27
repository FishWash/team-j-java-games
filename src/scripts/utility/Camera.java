package scripts.utility;

import scripts.DisplayableElement;
import scripts.GameWorld;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Camera {
  protected Vector2 position;

  public Camera(){
    this.position = new Vector2(0,0);
  }

  public static BufferedImage getMinimapDisplay(BufferedImage currentImage){
    double scale = 0.2;
    int minimapWidth = (int) (GameWorld.getInstance().getDimension().width * scale);
    int minimapHeight = (int) (GameWorld.getInstance().getDimension().width * scale);
    BufferedImage resizedMap = DisplayableElement.getScaledImage(currentImage, scale, minimapWidth, minimapHeight);

    return resizedMap;
  }
}
