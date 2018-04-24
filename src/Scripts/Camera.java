package Scripts;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Camera {

  public static BufferedImage getPlayerDisplay(BufferedImage currentImage, GameObject gameObject, int screenWidth, int screenHeight){
    int gameWidth = currentImage.getWidth();
    int gameHeight = currentImage.getHeight();
    Vector2 tankPosition = gameObject.getCenterPosition();

    int displayX = (int) Math.max(0, Math.min(gameWidth - screenWidth, tankPosition.x - screenWidth / 2));
    int displayY = (int) Math.max(0, Math.min(gameHeight - screenHeight, tankPosition.y - screenHeight / 2));

    BufferedImage playerDisplay = currentImage.getSubimage(displayX, displayY, screenWidth, screenHeight);

    return playerDisplay;
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
