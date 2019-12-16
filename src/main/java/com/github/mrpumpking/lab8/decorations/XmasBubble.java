package com.github.mrpumpking.lab8.decorations;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;

public class XmasBubble implements XmasShape {
  private double x;
  private double y;
  private double scale;
  private Color color;

  private static final int WIDTH = 25;
  private static final int HEIGHT = 25;

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
    XmasShape.setGradientFill(g2d, HEIGHT, color, color.brighter());
    //    g2d.setColor(color);
    g2d.fillOval((int) x, (int) y, WIDTH, HEIGHT);
  }
}
