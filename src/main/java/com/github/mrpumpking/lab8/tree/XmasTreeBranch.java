package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;

public class XmasTreeBranch implements XmasShape {
  private int scale;
  private Color color;
  private double xOffset;
  private double yOffset;

  public static final int BASE_WIDTH = 160;
  public static final int BASE_HEIGHT = 100;

  private static final int[] X_POINTS = new int[] {0, BASE_WIDTH / 2, BASE_WIDTH};
  private static final int[] Y_POINTS = new int[] {BASE_HEIGHT, 0, BASE_HEIGHT};

  public XmasTreeBranch(Color color, int scale) {
    this.color = color;
    this.scale = scale;
    this.xOffset = -BASE_WIDTH / 2D * scale;
    this.yOffset = BASE_HEIGHT * (scale - 2);
  }

  public double getYOffset() {
    return yOffset;
  }

  public int getHeight() {
    return BASE_HEIGHT * scale;
  }

  @Override
  public void transform(Graphics2D g2d) {
    g2d.translate(xOffset, yOffset);
    g2d.scale(scale, scale);
  }

  @Override
  public void render(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fillPolygon(X_POINTS, Y_POINTS, X_POINTS.length);
  }
}
