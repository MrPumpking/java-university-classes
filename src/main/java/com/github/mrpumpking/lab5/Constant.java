package com.github.mrpumpking.lab5;

public class Constant extends Node {
  double value;

  public Constant(double value) {
    this.sign = Sign.parse(value);
    this.value = Math.abs(value);
  }

  @Override
  double evaluate() {
    return sign.getValue() * value;
  }

  @Override
  public String toString() {
    return sign.getStringValue() + NODE_FORMAT.format(value);
  }
}
