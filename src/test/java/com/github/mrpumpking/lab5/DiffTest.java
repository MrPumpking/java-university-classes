package com.github.mrpumpking.lab5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiffTest {

  @Test
  void givenNodes_whenDiff_thenCalculateDerivative() {
    Variable x = new Variable("x");
    Node exp = new Sum().add(2, new Power(x, 3)).add(new Power(x, 2)).add(-2, x).add(7);
    assertEquals(exp.diff(x).toString(), "2*3*x^2*1 + 2*x^1*1 + (-2)*1");
  }

  @Test
  void givenNodes_whenDiffWithDifferentVariables_thenCalculateDerivative() {
    Variable x = new Variable("x");
    Variable y = new Variable("y");
    Node circle = new Sum().add(new Power(x, 2)).add(new Power(y, 2)).add(8, x).add(4, y).add(16);
    Node dx = circle.diff(x);
    Node dy = circle.diff(y);

    assertEquals(dx.toString(), "2*x^1*1 + 8*1");
    assertEquals(dy.toString(), "2*y^1*1 + 4*1");
  }
}
