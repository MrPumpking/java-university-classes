package com.github.mrpumpking.lab10;

import com.github.mrpumpking.lab10.decorations.XmasBubble;
import com.github.mrpumpking.lab10.decorations.XmasGift;
import com.github.mrpumpking.lab10.decorations.XmasStar;
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

    int treeHeight = 4;
    Color branchColor = Color.decode("#338266");

    String[] colors = new String[] {"#F79F79", "#EF767A", "#63ADF2", "#FAB3A9"};

    for (int i = treeHeight; i > 0; i--) {
      int scale = i;

      XmasTreeBranch branch = new XmasTreeBranch(branchColor, i + 1);
      shapes.add(branch);

      int halfBranchWidth = branch.getWidth() / 2;
      int bubbleSpacing = 40;

      for (int x = -halfBranchWidth + bubbleSpacing * 2 - 30;
          x < halfBranchWidth / 2 + bubbleSpacing * 2 - 30;
          x += XmasBubble.BASE_WIDTH + bubbleSpacing) {
        shapes.add(
            new XmasBubble(
                x,
                branch.getYOffset() + branch.getHeight() - XmasBubble.BASE_HEIGHT - 20,
                1,
                Color.decode(colors[i - 1])));
      }

      totalBranchHeight += branch.getHeight() - branch.getYOffset();
      branchColor = branchColor.brighter();
    }

    shapes.add(new XmasTreeTrunk(trunkWidth, trunkHeight, -trunkWidth / 2D, totalBranchHeight));
    shapes.add(
        new XmasGift(
            -400, totalBranchHeight + trunkHeight - 150, 250, 150, 1, Color.decode("#D8973C")));
    shapes.add(
        new XmasGift(
            -200, totalBranchHeight + trunkHeight - 100, 100, 100, 1, Color.decode(colors[2])));
    shapes.add(
        new XmasGift(
            200, totalBranchHeight + trunkHeight - 200, 200, 200, 1, Color.decode(colors[1])));
    shapes.add(
        new XmasGift(
            100, totalBranchHeight + trunkHeight - 100, 150, 100, 1, Color.decode(colors[0])));

    shapes.add(new XmasStar(0, -15, 25, Color.decode("#D8973C")));
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
