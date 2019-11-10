package com.github.mrpumpking.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Prod extends Node {
  private List<Node> args = new ArrayList<>();

  public Prod() {}

  public Prod(Node... nodes) {
    args.addAll(Arrays.asList(nodes));
  }

  public Prod(double... constants) {
    args.addAll(Arrays.stream(constants).mapToObj(Constant::new).collect(Collectors.toList()));
  }

  public Prod(double constant, Node node) {
    this(new Constant(constant), node);
  }

  public Prod multiply(Node... nodes) {
    args.addAll(Arrays.asList(nodes));
    return this;
  }

  public Prod multiply(double... constants) {
    args.addAll(Arrays.stream(constants).mapToObj(Constant::new).collect(Collectors.toList()));
    return this;
  }

  @Override
  int getArgumentsCount() {
    return args.size();
  }

  @Override
  double evaluate() {
    return sign.getValue()
        * args.stream().mapToDouble(Node::evaluate).reduce(1, (product, value) -> product * value);
  }

  @Override
  Node diff(Variable variable) {
    Sum result = new Sum();
    for (int i = 0; i < args.size(); i++) {
      Prod prod = new Prod();
      for (int j = 0; j < args.size(); j++) {
        Node f = args.get(j);
        if (j == i) {
          prod.multiply(f.diff(variable));
        } else {
          prod.multiply(f);
        }
      }

      if (!prod.isDiffZero(variable)) {
        result.add(prod);
      }
    }
    return result;
  }

  @Override
  boolean isDiffZero(Variable variable) {
    return args.stream().anyMatch(node -> (node instanceof Constant && node.evaluate() == 0));
  }

  @Override
  public String toString() {
    if (args.stream().anyMatch(node -> (node instanceof Constant && node.evaluate() == 0))) {
      return new Constant(0).toString();
    }

    StringBuilder builder = new StringBuilder("");

    if (sign == Sign.MINUS) {
      builder.append(sign.getStringValue());
      builder.append("(");
    }

    StringJoiner joiner = new StringJoiner("*");
    args.stream()
        .filter(node -> !node.toString().equals("0") && !node.toString().isEmpty())
        .forEach(node -> joiner.add(node.toString()));
    builder.append(joiner.toString());

    if (sign == Sign.MINUS) {
      builder.append(")");
    }

    return builder.toString();
  }
}
