package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;

public class XmasTreeTrunk implements XmasShape {
  private int width;
  private int height;
  private double xOffset;
  private double yOffset;

  public XmasTreeTrunk(int width, int height, double xOffset, double yOffset) {
    this.width = width;
    this.height = height;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  @Override
  public void transform(Graphics2D g2d) {
    g2d.translate(xOffset, yOffset);
  }

  @Override
  public void render(Graphics2D g2d) {
    XmasShape.setGradientFill(
        g2d, height, Color.decode("#533E2D"), Color.decode("#533E2D").brighter());
    g2d.fillRect(0, 0, width, height);
  }
}
