package com.github.mrpumpking.lab5;

import java.util.Locale;

public class Main {

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    System.out.println("=== Print expression ===");
    buildAndPrint();

    System.out.println("=== Evaluate expression ===");
    buildAndEvaluate();

    System.out.println("=== Define circle ===");
    defineCircle();
  }

  private void buildAndPrint() {
    Variable x = new Variable("x");
    Node exp = new Sum().add(2.1, new Power(x, 3)).add(new Power(x, 2)).add(-2, x).add(7);
    System.out.println(exp.toString());
  }

  private void buildAndEvaluate() {
    Variable x = new Variable("x");
    Node exp = new Sum().add(new Power(x, 3)).add(-2, new Power(x, 2)).add(-1, x).add(2);

    for (double v = -5; v < 5; v += 0.1) {
      x.setValue(v);
      System.out.printf(Locale.US, "f(%f)=%f\n", v, exp.evaluate());
    }
  }

  private void defineCircle() {
    Variable x = new Variable("x");
    Variable y = new Variable("y");
    Node circle = new Sum().add(new Power(x, 2)).add(new Power(y, 2)).add(8, x).add(4, y).add(16);
    System.out.println("Circle equation: " + circle.toString());

    for (int i = 0; i < 100; i++) {
      double[] coords = getRandomPointInsideCircle(-4, -2, 2);
      x.setValue(coords[0]);
      y.setValue(coords[1]);
      double value = circle.evaluate();

      System.out.printf(
          "[%d] Punkt [%f, %f] leży %s koła %s",
          i, coords[0], coords[1], (value < 0 ? "wewnątrz" : "na zewnątrz"), circle.toString());
      System.out.println();
    }
  }

  /** @see https://programming.guide/random-point-within-circle.html */
  private double[] getRandomPointInsideCircle(double xCenter, double yCenter, double radius) {
    double a = Math.random() * 2 * Math.PI;
    double r = radius * Math.sqrt(Math.random());

    double x = xCenter + r * Math.cos(a);
    double y = yCenter + r * Math.sin(a);

    return new double[] {x, y};
  }
}
