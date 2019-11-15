package com.github.mrpumpking.lab6.exceptions;

public class ColumnNotFoundException extends Exception {
  public ColumnNotFoundException(String column) {
    super(String.format("Column \"%s\" not found", column));
  }
}
