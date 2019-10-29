package com.github.mrpumpking.lab5;

public class Constant extends Node {
  double value;

  public Constant(double value) {
    this.sign = value < 0 ? -1 : 1;
    this.value = Math.abs(value);
  }

  @Override
  double evaluate() {
    return sign * value;
  }

  @Override
  public String toString() {
    return (sign < 0 ? "-" : "") + NODE_FORMAT.format(value);
  }
}
