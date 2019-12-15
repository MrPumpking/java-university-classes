package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class XmasTree implements XmasShape {
  private double xOffset;
  private double yOffset;
  private XmasTreeTrunk trunk;
  private List<XmasTreeBranch> branches;

  public XmasTree(int height, int windowWidth, int windowHeight) {
    this.branches = new ArrayList<>();
    Color color = Color.decode("#338266");

    double totalHeight = 0;
    int maxWidth = XmasTreeBranch.BASE_WIDTH * height;
    int maxHeight = XmasTreeBranch.BASE_HEIGHT * height;

    for (int i = height - 1; i >= 0; i--) {
      int scale = i + 1;

      double currentWidth = XmasTreeBranch.BASE_WIDTH * scale;
      double currentHeight = XmasTreeBranch.BASE_HEIGHT * scale;

      double xOffset = (maxWidth - currentWidth) / 2;
      double yOffset = (currentHeight / 2) - (XmasTreeBranch.BASE_HEIGHT / 2D);

      branches.add(new XmasTreeBranch(color, i + 1, xOffset, yOffset));

      color = color.brighter();
      totalHeight += currentHeight - yOffset;
    }

    this.xOffset = (windowWidth - XmasTreeBranch.BASE_WIDTH * height) / 2D;
    this.yOffset = windowHeight - totalHeight + 33;

    this.trunk = new XmasTreeTrunk(50, 80, 290, 550);
  }

  @Override
  public void transform(Graphics2D g2d) {
    g2d.translate(xOffset, yOffset);
  }

  @Override
  public void render(Graphics2D g2d) {}

  @Override
  public void draw(Graphics2D g2d) {
    AffineTransform saveAT = g2d.getTransform();
    transform(g2d);
    branches.forEach(branch -> branch.draw(g2d));
    trunk.draw(g2d);
    g2d.setTransform(saveAT);
  }
}
