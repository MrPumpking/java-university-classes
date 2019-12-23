package com.github.mrpumpking.lab10.decorations;

import com.github.mrpumpking.lab10.XmasShape;

import java.awt.*;

public class XmasBubble implements XmasShape {
  private double x;
  private double y;
  private double scale;
  private Color color;

  public static final int BASE_WIDTH = 25;
  public static final int BASE_HEIGHT = 25;

  public XmasBubble(double x, double y, double scale, Color color) {
    this.x = x;
    this.y = y;
    this.scale = scale;
    this.color = color;
  }

  @Override
  public void transform(Graphics2D g2d) {
    g2d.translate(x, y);
    g2d.scale(scale, scale);
  }

  @Override
  public void render(Graphics2D g2d) {
    XmasShape.setGradientFill(g2d, BASE_HEIGHT, color.brighter(), color);
    g2d.fillOval(0, 0, BASE_WIDTH, BASE_HEIGHT);
  }
}
