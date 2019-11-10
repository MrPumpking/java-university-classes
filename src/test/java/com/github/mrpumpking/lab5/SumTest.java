package com.github.mrpumpking.lab5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumTest {

  @Test
  void givenNodes_whenToString_thenReturnFormattedEquation() {
    Variable x = new Variable("x");
    Node exp = new Sum().add(2.1, new Power(x, 3)).add(new Power(x, 2)).add(-2, x).add(7);
    assertEquals(exp.toString(), "2.1*x^3 + x^2 + (-2)*x + 7");
  }

  @Test
  void givenNodes_whenEvaluate_thenReturnEvaluatedExpression() {
    Variable x = new Variable("x");
    Node exp = new Sum().add(new Power(x, 3)).add(-2, new Power(x, 2)).add(-1, x).add(2);
    x.setValue(-5);
    assertEquals(exp.evaluate(), -168, 0.001);
  }
}
