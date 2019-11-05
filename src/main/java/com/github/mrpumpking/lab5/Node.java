package com.github.mrpumpking.lab5;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class Node {
  Sign sign = Sign.PLUS;

  static final DecimalFormat NODE_FORMAT =
      new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));

  Node minus() {
    sign = Sign.MINUS;
    return this;
  }

  Node plus() {
    sign = Sign.PLUS;
    return this;
  }

  Sign getSign() {
    return sign;
  }

  abstract double evaluate();

  int getArgumentsCount() {
    return 0;
  }
}
