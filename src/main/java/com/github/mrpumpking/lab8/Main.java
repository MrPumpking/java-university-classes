package com.github.mrpumpking.lab8;

import javax.swing.*;

public class Main {

  public static void main(String[] args) {
    // write your code here
    JFrame frame = new JFrame("Choinka");
    frame.setContentPane(new DrawPanel());
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setResizable(true);
    frame.setVisible(true);
  }
}
