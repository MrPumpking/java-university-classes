package com.github.mrpumpking.lab8;

import javax.swing.*;

public class Main {

  private static final int WIDTH = 1600;
  private static final int HEIGHT = 900;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          JFrame frame = new JFrame("Choinka");
          frame.setContentPane(new DrawPanel(WIDTH, HEIGHT));
          frame.setSize(WIDTH, HEIGHT);
          frame.setLocationRelativeTo(null);
          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          frame.setResizable(true);
          frame.setVisible(true);
        });
  }
}
