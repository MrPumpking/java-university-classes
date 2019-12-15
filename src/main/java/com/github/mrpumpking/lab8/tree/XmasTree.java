package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class XmasTree implements XmasShape {
  private int xOffset;
  private int yOffset;
  private List<XmasTreeBranch> branches;

  public XmasTree(int height, int windowWidth, int windowHeight) {
    this.branches = new ArrayList<>();
    Color color = Color.decode("#338266");

    for (int i = height - 1; i >= 0; i--) {
      branches.add(new XmasTreeBranch(color, i + 1, height));
      color = color.brighter();
    }

    this.xOffset = (windowWidth - XmasTreeBranch.BASE_WIDTH * height) / 2;
    this.yOffset = (windowHeight - XmasTreeBranch.BASE_HEIGHT * height) / 2;
  }

  @Override
  public void transform(Graphics2D g2d) {
    System.out.println(xOffset);
    g2d.translate(xOffset, yOffset);
  }

  @Override
  public void render(Graphics2D g2d) {}

  @Override
  public void draw(Graphics2D g2d) {
    AffineTransform saveAT = g2d.getTransform();
    transform(g2d);
    branches.forEach(branch -> branch.draw(g2d));
    g2d.setTransform(saveAT);
  }
}
