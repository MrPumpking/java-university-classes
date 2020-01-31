package com.github.mrpumpking.lab14;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class BouncingBallsPanel extends JPanel implements ComponentListener {
  private Image bufferImage;
  private long lastPaint = 0;
  private boolean initialised = false;
  private Random random = new Random();
  private List<Ball> balls = new ArrayList<>();

  private final AtomicBoolean paused = new AtomicBoolean(true);
  private static final int FRAME_RATE = 60;
  private static final int SLEEP_TIME = 1;

  BouncingBallsPanel() {
    setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
    addComponentListener(this);
    new AnimationThread().start();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    balls.forEach(
        ball -> {
          g.setColor(ball.color);
          g.fillOval(
              (int) ball.x - Ball.BALL_SIZE,
              (int) ball.y - Ball.BALL_SIZE,
              2 * Ball.BALL_SIZE,
              2 * Ball.BALL_SIZE);

          g.setColor(Color.BLACK);
          g.drawOval(
              (int) ball.x - Ball.BALL_SIZE,
              (int) ball.y - Ball.BALL_SIZE,
              2 * Ball.BALL_SIZE,
              2 * Ball.BALL_SIZE);
        });
  }

  @Override
  public void update(Graphics g) {
    if (System.currentTimeMillis() - lastPaint < FRAME_RATE) {
      return;
    }

    Dimension dim = getSize();
    if (bufferImage == null
        || bufferImage.getHeight(this) != dim.height
        || bufferImage.getWidth(this) != dim.width) {
      bufferImage = createImage(dim.width, dim.height);
    }

    Graphics bufferGraphics = bufferImage.getGraphics();
    bufferGraphics.setColor(Color.LIGHT_GRAY);
    bufferGraphics.fillRect(0, 0, dim.width, dim.height);
    super.paint(bufferGraphics);
    g.drawImage(bufferImage, 0, 0, this);
    lastPaint = System.currentTimeMillis();
  }

  private void updatePhysics() {
    balls.forEach(
        ball -> {
          handleMovement(ball);
          handleCollision(ball);

          balls.forEach(
              other -> {
                if (ball != other && ball.getBoundingRect().intersects(other.getBoundingRect())) {
                  handleBallCollision(ball, other);
                }
              });
        });
  }

  private void handleMovement(Ball ball) {
    ball.x += ball.acceleration * ball.vx;
    ball.y += ball.acceleration * ball.vy;
  }

  private void handleCollision(Ball ball) {
    Dimension d = getSize();
    if (ball.x < Ball.BALL_SIZE) {
      ball.x = Ball.BALL_SIZE;
      ball.vx *= -1;
    }
    if (ball.x > d.width - Ball.BALL_SIZE) {
      ball.x = d.width - Ball.BALL_SIZE;
      ball.vx *= -1;
    }
    if (ball.y < Ball.BALL_SIZE) {
      ball.y = Ball.BALL_SIZE;
      ball.vy *= -1;
    }
    if (ball.y > d.height - Ball.BALL_SIZE) {
      ball.y = d.height - Ball.BALL_SIZE;
      ball.vy *= -1;
    }
  }

  /** Zderzenia między piłkami działają tak sobie :) */
  private void handleBallCollision(Ball first, Ball second) {
    double tempVX = first.vx;
    double tempVY = first.vy;

    first.vx = second.vx;
    first.vy = second.vy;

    second.vx = tempVX;
    second.vy = tempVY;
  }

  synchronized void onStart() {
    synchronized (paused) {
      paused.set(false);
      paused.notifyAll();
      System.out.println("Start or resume animation thread");
    }
  }

  void onStop() {
    synchronized (paused) {
      paused.set(true);
      paused.notifyAll();
      System.out.println("Suspend animation thread");
    }
  }

  void onPlus() {
    System.out.println("Add a ball");
    generateRandomBall();
    repaint();
  }

  void onMinus() {
    if (balls.isEmpty()) {
      return;
    }

    System.out.println("Remove a ball");
    balls.remove(random.ints(1, 0, balls.size()).toArray()[0]);
    repaint();
  }

  private void generateRandomBalls(int count) {
    for (int i = 0; i < count; i++) {
      generateRandomBall();
    }
  }

  private void generateRandomBall() {
    int vx = (random.nextDouble() > 0.5) ? -1 : 1;
    int vy = (random.nextDouble() > 0.5) ? -1 : 1;

    int x = pickRandomPointBetween(Ball.BALL_SIZE * 2, getWidth() - Ball.BALL_SIZE * 2);
    int y = pickRandomPointBetween(Ball.BALL_SIZE * 2, getHeight() - Ball.BALL_SIZE * 2);

    int acceleration = random.ints(1, 1, 3).findFirst().orElse(1);

    balls.add(new Ball(x, y, vx, vy, acceleration, new Color((int) (Math.random() * 0x1000000))));
  }

  private int pickRandomPointBetween(int start, int end) {
    return (int) (Math.abs(start - end) * random.nextDouble());
  }

  @Override
  public void componentResized(ComponentEvent e) {
    if (!initialised && getWidth() != 0 && getHeight() != 0) {
      initialised = true;
      generateRandomBalls(10);
    }

    System.out.println(balls);
  }

  @Override
  public void componentMoved(ComponentEvent e) {}

  @Override
  public void componentShown(ComponentEvent e) {}

  @Override
  public void componentHidden(ComponentEvent e) {}

  static class Ball {
    int x;
    int y;
    double vx = 1;
    double vy = 1;
    double acceleration = 1;
    Color color;
    private Rectangle bbox;
    public static int BALL_SIZE = 10;

    public Ball(int x, int y, int vx, int vy, int acceleration, Color color) {
      this.x = x;
      this.y = y;
      this.vx = vx;
      this.vy = vy;
      this.color = color;
      this.acceleration = acceleration;
    }

    public Rectangle getBoundingRect() {
      if (bbox == null) {
        bbox = new Rectangle();
      }

      int xPos = x - BALL_SIZE / 2;
      int yPos = y - BALL_SIZE / 2;

      bbox.setBounds(xPos, yPos, BALL_SIZE, BALL_SIZE);
      return bbox;
    }
  }

  class AnimationThread extends Thread {

    @Override
    public void run() {
      while (true) {
        try {
          synchronized (paused) {
            if (paused.get()) {
              paused.wait();
            }
          }

          sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        updatePhysics();
        repaint();
      }
    }
  }
}
