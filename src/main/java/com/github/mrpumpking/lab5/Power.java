package com.github.mrpumpking.lab5;

public class Power extends Node {
  private Node base;
  private double exponent;

  public Power(Node base, double exponent) {
    this.base = base;
    this.exponent = exponent;
  }

  @Override
  int getArgumentsCount() {
    return 1;
  }

  @Override
  double evaluate() {
    return Math.pow(base.evaluate(), exponent);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("");
    boolean useParentheses = (sign == Sign.MINUS || base.getArgumentsCount() > 0);

    builder.append(sign.getStringValue());

    if (useParentheses) {
      builder.append("(");
    }

    builder.append(base.toString());

    if (useParentheses) {
      builder.append(")");
    }

    builder.append("^");
    builder.append(exponent);

    return builder.toString();
  }
}
