package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;

public class XmasTreeBranch implements XmasShape {
  private int scale;
  private int maxWidth;
  private Color color;

  static final int BASE_WIDTH = 160;
  static final int BASE_HEIGHT = 100;

  private static final int[] X_POINTS = new int[] {0, BASE_WIDTH / 2, BASE_WIDTH};
  private static final int[] Y_POINTS = new int[] {BASE_HEIGHT, 0, BASE_HEIGHT};

  public XmasTreeBranch(Color color, int scale, int maxScale) {
    this.color = color;
    this.scale = scale;
    this.maxWidth = BASE_WIDTH * maxScale;
  }

  @Override
  public void transform(Graphics2D g2d) {
    double currentWidth = BASE_WIDTH * scale;
    double currentHeight = BASE_HEIGHT * scale;

    double xOffset = (maxWidth - currentWidth) / 2;
    double yOffset = (currentHeight / 2) - (BASE_HEIGHT / 2D);

    g2d.translate(xOffset, yOffset);
    g2d.scale(scale, scale);
  }

  @Override
  public void render(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fillPolygon(X_POINTS, Y_POINTS, X_POINTS.length);
  }
}
