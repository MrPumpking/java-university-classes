package com.github.mrpumpking.lab10;

import com.github.mrpumpking.lab10.decorations.XmasBubble;
import com.github.mrpumpking.lab10.tree.XmasTreeBranch;
import com.github.mrpumpking.lab10.tree.XmasTreeTrunk;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
  private int totalBranchHeight;
  private List<XmasShape> shapes;

  private int trunkWidth = 100;
  private int trunkHeight = 100;

  public DrawPanel() {
    this.shapes = new ArrayList<>();
    //    shapes.add(new XmasTree(4, width, height));

    int treeHeight = 4;
    Color branchColor = Color.decode("#338266");

    String[] colors = new String[] {"#F79F79", "#EF767A", "#63ADF2", "#FAB3A9"};

    for (int i = treeHeight; i > 0; i--) {
      int scale = i;

      XmasTreeBranch branch = new XmasTreeBranch(branchColor, i + 1);
      shapes.add(branch);

      for (int j = -XmasTreeBranch.BASE_WIDTH * scale / 2;
          j < XmasTreeBranch.BASE_WIDTH * scale / 2;
          j += XmasTreeBranch.BASE_WIDTH / 4) {
        shapes.add(
            new XmasBubble(j + 20, branch.getYOffset() + 70, 1, Color.decode(colors[i - 1])));
      }

      totalBranchHeight += branch.getHeight() - branch.getYOffset();
      branchColor = branchColor.brighter();
    }

    shapes.add(new XmasTreeTrunk(trunkWidth, trunkHeight, -trunkWidth / 2D, totalBranchHeight));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    setBackgroundColor(g2d);
    g2d.translate(getWidth() / 2, getHeight() - totalBranchHeight - trunkHeight);
    shapes.forEach(shape -> shape.draw(g2d));
  }

  private void setBackgroundColor(Graphics2D g2d) {
    XmasShape.setGradientFill(g2d, getHeight(), Color.decode("#462255"), Color.decode("#2B193D"));
    g2d.fillRect(0, 0, getWidth(), getHeight());
  }
}
