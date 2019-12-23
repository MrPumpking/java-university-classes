package com.github.mrpumpking.lab10.decorations;

import com.github.mrpumpking.lab10.XmasShape;

import java.awt.*;
import java.awt.geom.Path2D;

public class XmasStar implements XmasShape {
  private Color color;
  private int xOffset;
  private int yOffset;
  private double radius;
  private Shape starShape;

  public XmasStar(int x, int y, double radius, Color color) {
    xOffset = x;
    yOffset = y;
    this.color = color;
    this.radius = radius;
    starShape = createDefaultStar(radius, 0, 0);
  }

  @Override
  public void transform(Graphics2D g2d) {
    g2d.translate(xOffset, yOffset);
  }

  @Override
  public void render(Graphics2D g2d) {
    XmasShape.setGradientFill(g2d, (int) (radius * 2), color.brighter(), color);
    g2d.fill(starShape);
  }

  private Shape createDefaultStar(double radius, double centerX, double centerY) {
    return createStar(centerX, centerY, radius, radius * 2.63, 5, Math.toRadians(-18));
  }

  /** @link https://stackoverflow.com/a/39808564 */
  private Shape createStar(
      double centerX,
      double centerY,
      double innerRadius,
      double outerRadius,
      int numRays,
      double startAngleRad) {
    Path2D path = new Path2D.Double();
    double deltaAngleRad = Math.PI / numRays;
    for (int i = 0; i < numRays * 2; i++) {
      double angleRad = startAngleRad + i * deltaAngleRad;
      double ca = Math.cos(angleRad);
      double sa = Math.sin(angleRad);
      double relX = ca;
      double relY = sa;
      if ((i & 1) == 0) {
        relX *= outerRadius;
        relY *= outerRadius;
      } else {
        relX *= innerRadius;
        relY *= innerRadius;
      }
      if (i == 0) {
        path.moveTo(centerX + relX, centerY + relY);
      } else {
        path.lineTo(centerX + relX, centerY + relY);
      }
    }
    path.closePath();
    return path;
  }
}
