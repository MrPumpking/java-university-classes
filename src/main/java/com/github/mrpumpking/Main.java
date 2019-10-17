package com.github.mrpumpking;

import com.github.mrpumpking.lab2.Matrix;

public class Main {
  public static void main(String[] args) {
    Matrix matrix =
        new Matrix(
            new double[][] {
              {1, 2, 3},
              {4, 8, 6},
              {7, 8, 9}
            });

    Matrix other = new Matrix(matrix);

    System.out.println(matrix);
    System.out.println(matrix.determinant());
    System.out.println(matrix.inverse());
    System.out.println(matrix.multiply(other));
  }
}
