package Scripts;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Camera {
  protected Vector2 position;

  public Camera(){
    this.position = new Vector2(0,0);
  }

  public static BufferedImage getMinimapDisplay(BufferedImage currentImage){
    int minimapSize = TankGameApplication.gameDimension.width / 5;
    BufferedImage resizedMap = new BufferedImage(minimapSize, minimapSize, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    at.scale(0.2, 0.2);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    resizedMap = scaleOp.filter(currentImage, resizedMap);

    return resizedMap;
  }
}
