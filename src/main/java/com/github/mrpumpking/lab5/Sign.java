package com.github.mrpumpking.lab5;

public enum Sign {
  PLUS(1.0, ""),
  MINUS(-1.0, "-");

  private double value;
  private String stringValue;

  Sign(double value, String stringValue) {
    this.value = value;
    this.stringValue = stringValue;
  }

  public double getValue() {
    return value;
  }

  public String getStringValue() {
    return stringValue;
  }

  public static Sign parse(double value) {
    return value < 0 ? MINUS : PLUS;
  }
}
