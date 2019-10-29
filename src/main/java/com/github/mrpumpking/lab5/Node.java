package com.github.mrpumpking.lab5;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class Node {
  int sign = 1;

  static final DecimalFormat NODE_FORMAT =
      new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));

  Node minus() {
    sign = -1;
    return this;
  }

  Node plus() {
    sign = 1;
    return this;
  }

  int getSign() {
    return sign;
  }

  abstract double evaluate();

  int getArgumentsCount() {
    return 0;
  }
}
