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

  @Override
  double evaluate() {
    return sign.getValue() * value;
  }

  @Override
  public String toString() {
    return sign.getStringValue() + name;
  }
}
