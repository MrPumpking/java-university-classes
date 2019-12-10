package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;

public class XmasTreeBranch implements XmasShape {
  private int scale;
  private int position;
  private boolean last;
  private Color color;

  private static final int[] xPoints = new int[] {0, 100, 200};
  private static final int[] yPoints = new int[] {100, 0, 100};

  public XmasTreeBranch(Color color, int scale, int position, boolean last) {
    this.last = last;
    this.color = color;
    this.scale = scale;
  }

  @Override
  public void transform(Graphics2D g2d) {
    System.out.println(last + ":" + scale);
    g2d.translate((!last ? (200 * (scale + 1) / 4) : 0), position * scale * 100 * 2);
    g2d.scale(scale, scale);
  }

  @Override
  public void render(Graphics2D g2d) {
    g2d.setColor(color);
    g2d.fillPolygon(xPoints, yPoints, xPoints.length);
  }
}
