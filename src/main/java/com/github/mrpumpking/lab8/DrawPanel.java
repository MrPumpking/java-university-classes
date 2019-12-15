package com.github.mrpumpking.lab8;

import com.github.mrpumpking.lab8.tree.XmasTree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
  private int width;
  private int height;
  private List<XmasShape> shapes;

  public DrawPanel(int width, int height) {
    this.width = width;
    this.height = height;
    this.shapes = new ArrayList<>();
    shapes.add(new XmasTree(4, width, height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    setBackgroundColor(g2d);
    shapes.forEach(shape -> shape.draw(g2d));
  }

  private void setBackgroundColor(Graphics2D g2d) {
    XmasShape.setGradientFill(g2d, getHeight(), Color.decode("#462255"), Color.decode("#2B193D"));
    g2d.fillRect(0, 0, getWidth(), getHeight());
  }
}
