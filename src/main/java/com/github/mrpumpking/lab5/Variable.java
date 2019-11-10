package com.github.mrpumpking.lab5;

public class Variable extends Node {
  private String name;
  private double value;

  public Variable(String name) {
    this.name = name;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  @Override
  double evaluate() {
    return sign.getValue() * value;
  }

  @Override
  boolean isDiffZero(Variable variable) {
    return !variable.getName().equals(name);
  }

  @Override
  Node diff(Variable variable) {
    if (variable.getName().equals(name)) {
      return new Constant(1);
    }
    return new Constant(0);
  }

  @Override
  public String toString() {
    return sign.getStringValue() + name;
  }
}
