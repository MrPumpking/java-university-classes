package com.github.mrpumpking.lab8;

import com.github.mrpumpking.lab8.tree.XmasTree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
  private List<XmasShape> shapes;

  public DrawPanel() {
    this.shapes = new ArrayList<>();

    shapes.add(new XmasTree(3));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    shapes.forEach(shape -> shape.draw((Graphics2D) g));
  }
}
