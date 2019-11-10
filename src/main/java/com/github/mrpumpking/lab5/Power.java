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
  Node diff(Variable variable) {
    Prod result = new Prod(sign.getValue() * exponent, new Power(base, exponent - 1));
    result.multiply(base.diff(variable));
    return result;
  }

  @Override
  boolean isDiffZero(Variable variable) {
    return false;
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
    builder.append(NODE_FORMAT.format(exponent));

    return builder.toString();
  }
}
