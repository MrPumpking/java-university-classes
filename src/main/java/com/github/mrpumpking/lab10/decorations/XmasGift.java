package com.github.mrpumpking.lab10.decorations;

import com.github.mrpumpking.lab10.XmasShape;

import java.awt.*;

public class XmasGift implements XmasShape {
  private double x;
  private double y;
  private double scale;
  private int width;
  private int height;
  private Color color;

  public static final int WRAP_SIZE = 20;

  public XmasGift(double x, double y, int width, int height, double scale, Color color) {
    this.x = x;
    this.y = y;
    this.scale = scale;
    this.color = color;
    this.width = width;
    this.height = height;
  }

  @Override
  public void transform(Graphics2D g2d) {
    g2d.translate(x, y);
    g2d.scale(scale, scale);
  }

  @Override
  public void render(Graphics2D g2d) {
    XmasShape.setGradientFill(g2d, height, color.brighter(), color);
    g2d.fillRect(0, 0, width, height);
    g2d.setColor(Color.WHITE);
    g2d.fillRect(width / 2 - WRAP_SIZE / 2, 0, WRAP_SIZE, height);
    g2d.fillRect(0, height / 2 - WRAP_SIZE / 2, width, WRAP_SIZE);
  }
}
