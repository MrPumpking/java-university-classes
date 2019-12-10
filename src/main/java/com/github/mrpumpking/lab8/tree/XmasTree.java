package com.github.mrpumpking.lab8.tree;

import com.github.mrpumpking.lab8.XmasShape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class XmasTree implements XmasShape {
  List<XmasTreeBranch> branches;

  public XmasTree(int height) {
    this.branches = new ArrayList<>();
    Color color = Color.GREEN;

    for (int i = height - 1; i >= 0; i--) {
      branches.add(new XmasTreeBranch(color, i + 1, 0, i == height - 1));
      color = color.darker();
    }
  }

  @Override
  public void transform(Graphics2D g2d) {}

  @Override
  public void render(Graphics2D g2d) {}

  @Override
  public void draw(Graphics2D g2d) {
    XmasShape.super.draw(g2d);
    branches.forEach(branch -> branch.draw(g2d));
  }
}
