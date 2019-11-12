package com.github.mrpumpking.lab2_3;

public class Main {

  public static void main(String[] args) {
    /** Sprawdzian 12.11.2019 | Groupa F */
    Matrix m = new Matrix(new double[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    Matrix col = m.getSubmatrix(1, 3, 1, 3);
    System.out.println(col);
  }
}
