package com.github.mrpumpking.lab11;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGUI extends JPanel {
  private ClockThread thread;
  private LocalTime time = LocalTime.now();
  private static final int CLOCK_FACE_SIZE = 270;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Clock");
    frame.setContentPane(new ClockWithGUI());
    frame.setSize(700, 700);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.setVisible(true);
  }

  public ClockWithGUI() {
    super();
    thread = new ClockThread();
    thread.start();
  }

  public void paintComponent(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    int numberDistance = (int) (CLOCK_FACE_SIZE * 0.43);
    int hourHandLength = (int) (CLOCK_FACE_SIZE * 0.3);
    int secondHandLength = (int) (CLOCK_FACE_SIZE * 0.4);
    int minuteHandLength = (int) (CLOCK_FACE_SIZE * 0.35);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    g2d.translate(getWidth() / 2, getHeight() / 2);
    Point2D src = new Point2D.Float(0, -numberDistance);

    AffineTransform saveAT = g2d.getTransform();

    double minuteMove = time.getMinute() * 2 * Math.PI / 12 / 60;
    g2d.setColor(Color.BLUE);
    g2d.rotate(time.getHour() % 12 * 2 * Math.PI / 12 + minuteMove);
    g2d.drawLine(0, 0, 0, -hourHandLength);
    g2d.setTransform(saveAT);

    g2d.setColor(Color.RED);
    g2d.rotate(time.getMinute() * 2 * Math.PI / 60);
    g2d.drawLine(0, 0, 0, -minuteHandLength);
    g2d.setTransform(saveAT);

    g2d.setColor(Color.GREEN);
    g2d.rotate(time.getSecond() * 2 * Math.PI / 60);
    g2d.drawLine(0, 0, 0, -secondHandLength);
    g2d.setTransform(saveAT);

    g2d.setColor(Color.BLACK);
    g2d.drawOval(-CLOCK_FACE_SIZE / 2, -CLOCK_FACE_SIZE / 2, CLOCK_FACE_SIZE, CLOCK_FACE_SIZE);

    for (int i = 1; i < 61; i++) {
      boolean hour = i % 5 == 0;
      g2d.rotate(2 * Math.PI / 60 * i);
      g2d.drawLine(0, -CLOCK_FACE_SIZE / 2, 0, -CLOCK_FACE_SIZE / 2 + (hour ? 10 : 5));
      g2d.setTransform(saveAT);
    }

    g2d.setColor(Color.blue);
    for (int i = 1; i < 13; i++) {
      AffineTransform at = new AffineTransform();
      at.rotate(2 * Math.PI / 12 * i);
      Point2D trg = new Point2D.Float();
      at.transform(src, trg);

      String text = Integer.toString(i);
      int x = (int) (trg.getX() - g2d.getFontMetrics().stringWidth(text) / 2);
      int y = (int) (trg.getY() + g2d.getFontMetrics().getHeight() / 2 - 1);

      g2d.drawString(text, x, y);
    }
  }

  class ClockThread extends Thread {
    @Override
    public void run() {
      while (true) {
        time = LocalTime.now();
        System.out.printf("%02d:%02d:%02d\n", time.getHour(), time.getMinute(), time.getSecond());
        try {
          sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        repaint();
      }
    }
  }
}
