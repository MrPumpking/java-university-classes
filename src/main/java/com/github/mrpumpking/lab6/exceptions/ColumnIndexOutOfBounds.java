package com.github.mrpumpking.lab6.exceptions;

public class ColumnIndexOutOfBounds extends Exception {
  public ColumnIndexOutOfBounds(int index, int size) {
    super(String.format("Column index must be within [0-%d]. Recieved index = %d", size, index));
  }
}
