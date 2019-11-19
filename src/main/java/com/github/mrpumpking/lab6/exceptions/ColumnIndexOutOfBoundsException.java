package com.github.mrpumpking.lab6.exceptions;

public class ColumnIndexOutOfBoundsException extends Exception {
  public ColumnIndexOutOfBoundsException(int index, int size) {
    super(String.format("Column index must be within [0-%d]. Recieved index = %d", size, index));
  }
}
