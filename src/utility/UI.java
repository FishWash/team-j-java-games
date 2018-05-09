package utility;

import general.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class UI {

  public static BufferedImage getScaledImage(BufferedImage imageToResize,
                                             double xScale, double yScale,
                                             int newWidth, int newHeight) {
    BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    AffineTransform at = new AffineTransform();
    at.scale(xScale, yScale);
    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    scaledImage = scaleOp.filter(imageToResize, scaledImage);

    return scaledImage;
  }

  public static void drawPositionedText(
          Graphics graphics, String textToDisplay, Font font,
          double dimensionWidthProportion, double dimensionHeightProportion
  ) {
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setFont(font);
    FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
    Dimension panelDimension = GamePanel.getInstance().getSize();
    int xPos = (int) (panelDimension.getWidth()*dimensionWidthProportion
            - fontMetrics.stringWidth(textToDisplay)/2);
    int yPos = (int) (panelDimension.getHeight()*dimensionHeightProportion)
            + fontMetrics.getHeight()/2;
    graphics2D.drawString(textToDisplay, xPos, yPos);
  }
}
