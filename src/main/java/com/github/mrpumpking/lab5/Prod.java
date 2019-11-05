package com.github.mrpumpking.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Prod extends Node {
  private List<Node> args;

  public Prod(Node... elements) {
    this.args = new ArrayList<>();
    this.multiply(elements);
  }

  public Prod multiply(Node... elements) {
    args.addAll(Arrays.asList(elements));
    return this;
  }

  public Prod multiply(double... constants) {
    args.addAll(Arrays.stream(constants).mapToObj(Constant::new).collect(Collectors.toList()));
    return this;
  }

  public Prod multiply(double constant, Node var) {
    multiply(new Constant(constant), var);
  }

  @Override
  double evaluate() {
    return 0;
  }
}
