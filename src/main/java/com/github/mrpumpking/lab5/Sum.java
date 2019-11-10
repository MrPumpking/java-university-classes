package com.github.mrpumpking.lab5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Sum extends Node {
  private List<Node> args = new ArrayList<>();

  public Sum() {}

  public Sum(Node... nodes) {
    this.add(nodes);
  }

  public Sum(double... constants) {
    this.add(constants);
  }

  public Sum(double constant, Node node) {
    this.add(constant, node);
  }

  public Sum add(Node... nodes) {
    args.addAll(Arrays.asList(nodes));
    return this;
  }

  public Sum add(double... constants) {
    args.addAll(Arrays.stream(constants).mapToObj(Constant::new).collect(Collectors.toList()));
    return this;
  }

  public Sum add(double constant, Node node) {
    args.add(new Prod(constant, node));
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
  Node diff(Variable variable) {
    return new Sum(
        args.stream()
            .filter(node -> !node.isDiffZero(variable))
            .map(node -> node.diff(variable))
            .toArray(Node[]::new));
  }

  @Override
  boolean isDiffZero(Variable variable) {
    return args.stream().allMatch(node -> node.isDiffZero(variable));
  }

  @Override
  public String toString() {
    if (args.size() == 0) {
      return new Constant(0).toString();
    }

    StringBuilder builder = new StringBuilder("");

    if (sign == Sign.MINUS) {
      builder.append(sign.getStringValue());
      builder.append("(");
    }

    StringJoiner joiner = new StringJoiner(" + ");
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
