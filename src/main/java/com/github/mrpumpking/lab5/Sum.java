package com.github.mrpumpking.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Sum extends Node {
  private List<Node> args;

  public Sum(Node... elements) {
    this.args = new ArrayList<>();
    this.add(elements);
  }

  public Sum add(Node... node) {
    args.addAll(Arrays.asList(node));
    return this;
  }

  public Sum add(double... constants) {
    Arrays.stream(constants).forEach(value -> args.add(new Constant(value)));
    return this;
  }

  /**
   * TODO: implement multiplication
   *
   * @param constant
   * @param node
   */
  public Sum add(double constant, Node node) {
    Node multiply = null;
    return this;
  }

  @Override
  double evaluate() {
    return sign.getValue() * args.stream().mapToDouble(Node::evaluate).sum();
  }

  @Override
  int getArgumentsCount() {
    return args.size();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("");

    if (sign == Sign.MINUS) {
      builder.append(sign.getStringValue());
      builder.append("(");
    }

    StringJoiner joiner = new StringJoiner("+");
    args.forEach(node -> joiner.add(node.toString()));
    builder.append(joiner.toString());

    if (sign == Sign.MINUS) {
      builder.append(")");
    }

    return builder.toString();
  }
}
