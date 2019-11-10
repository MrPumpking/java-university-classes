package com.github.mrpumpking.lab5;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class Node {
  Sign sign = Sign.PLUS;

  static final double NODE_ZERO_PRECISION = 1e-10;
  static final DecimalFormat NODE_FORMAT =
      new DecimalFormat("#.#####", new DecimalFormatSymbols(Locale.US));

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

  abstract Node diff(Variable variable);

  abstract boolean isDiffZero(Variable variable);

  int getArgumentsCount() {
    return 0;
  }
}
