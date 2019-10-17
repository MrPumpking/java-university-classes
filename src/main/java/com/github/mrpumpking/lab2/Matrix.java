package com.github.mrpumpking.lab2;

import java.util.Arrays;
import java.util.StringJoiner;

public class Matrix {
  private int rows;
  private int cols;
  private double[] data;

  interface MatrixValueProcessor {
    public double process(double a, double b);
  }

  public Matrix(int rows, int cols) {
    init(rows, cols);
  }

  public Matrix(double[][] data) {
    int maxRowLength = 0;

    for (double[] row : data) {
      maxRowLength = Math.max(maxRowLength, row.length);
    }

    init(data.length, maxRowLength);
    copy(data);
  }

  private void init(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows * cols];
  }

  public Matrix(Matrix source) {
    init(source.getRows(), source.getCols());
    copy(source.asArray());
  }

  private void copy(double[][] data) {
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        try {
          set(y, x, data[y][x]);
        } catch (IndexOutOfBoundsException ignored) {
          break;
        }
      }
    }
  }

  double[] getData() {
    return data;
  }

  public double get(int index) {
    return data[index];
  }

  public void set(int index, double value) {
    data[index] = value;
  }

  public double get(int row, int col) {
    return data[row * cols + col];
  }

  public void set(int row, int col, double data) {
    this.data[row * cols + col] = data;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  public int[] getShape() {
    return new int[] {getRows(), getCols()};
  }

  public double[][] asArray() {
    double[][] array = new double[rows][cols];

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        array[y][x] = get(y, x);
      }
    }

    return array;
  }

  public String toString() {
    StringJoiner rowJoiner = new StringJoiner("", "[", "]");

    for (int y = 0; y < rows; y++) {
      StringJoiner joiner = new StringJoiner(",", "[", "]");

      for (int x = 0; x < cols; x++) {
        joiner.add(String.valueOf(get(y, x)));
      }

      rowJoiner.add(joiner.toString());
    }
    return rowJoiner.toString();
  }

  public void reshape(int newRows, int newCols) {
    if (rows * cols != newRows * newCols) {
      throw new IllegalArgumentException(
          String.format(
              "%d x %d matrix cannot be reshaped to %d x %d",
              getRows(), getCols(), newRows, newCols));
    }

    rows = newRows;
    cols = newCols;
  }

  Matrix processEachValue(Matrix other, MatrixValueProcessor processor) {
    if (!Arrays.equals(getShape(), other.getShape())) {
      throw new IllegalArgumentException(
          String.format(
              "Expected second matrix to be [%d x %d] but got [%d x %d]",
              getRows(), getCols(), other.getRows(), other.getCols()));
    }

    Matrix result = new Matrix(this);

    for (int i = 0; i < data.length; i++) {
      result.set(i, processor.process(result.get(i), other.get(i)));
    }

    return result;
  }

  Matrix processEachValueUsingScalar(double scalar, MatrixValueProcessor processor) {
    Matrix result = new Matrix(this);
    result.data =
        Arrays.stream(result.data).map(value -> processor.process(value, scalar)).toArray();
    return result;
  }

  public Matrix add(double scalar) {
    return processEachValueUsingScalar(scalar, (a, b) -> a + b);
  }

  public Matrix subtract(double scalar) {
    return processEachValueUsingScalar(scalar, (a, b) -> a - b);
  }

  public Matrix multiply(double scalar) {
    return processEachValueUsingScalar(scalar, (a, b) -> a * b);
  }

  public Matrix divide(double scalar) {
    return processEachValueUsingScalar(scalar, (a, b) -> a / b);
  }

  public Matrix minor(int row, int column) {
    Matrix minor = new Matrix(getRows() - 1, getCols() - 1);

    for (int y = 0; y < getRows(); y++) {
      for (int x = 0; y != row && x < getCols(); x++) {
        if (x != column) {
          minor.set(y < row ? y : y - 1, x < column ? x : x - 1, get(y, x));
        }
      }
    }

    return minor;
  }

  public Matrix add(Matrix other) {
    return processEachValue(other, (a, b) -> a + b);
  }

  public Matrix subtract(Matrix other) {
    return processEachValue(other, (a, b) -> a - b);
  }

  public double determinant() {
    if (getRows() != getCols()) {
      throw new IllegalStateException("Cannot calculate the determinant of non-square matrix");
    }

    if (getRows() == 1) {
      return get(0, 0);
    }

    if (getRows() == 2) {
      return get(0, 0) * get(1, 1) - get(0, 1) * get(1, 0);
    }

    double determinant = 0;

    for (int i = 0; i < getCols(); i++) {
      determinant += Math.pow(-1, i) * get(0, i) * minor(0, i).determinant();
    }

    return determinant;
  }

  public Matrix inverse() {
    double determinant = determinant();

    if (determinant == 0) {
      throw new IllegalStateException("Cannot calculate inverse of matrix with 0 determinant");
    }

    Matrix inverse = new Matrix(getRows(), getCols());

    // minors and cofactors
    for (int y = 0; y < inverse.getRows(); y++) {
      for (int x = 0; x < inverse.getCols(); x++) {
        inverse.set(y, x, Math.pow(-1, y + x) * minor(y, x).determinant() + 0.0);
      }
    }

    // adjugate and determinant
    double det = 1.0 / determinant;
    for (int y = 0; y < inverse.getRows(); y++) {
      for (int x = 0; x <= y; x++) {
        double temp = inverse.get(y, x);
        inverse.set(y, x, inverse.get(x, y) * det);
        inverse.set(x, y, temp * det);
      }
    }

    return inverse;
  }

  public Matrix transpose() {
    Matrix transposed = new Matrix(getCols(), getRows());

    for (int y = 0; y < getRows(); y++) {
      for (int x = 0; x < getCols(); x++) {
        transposed.set(x, y, get(y, x));
      }
    }

    return transposed;
  }

  public Matrix multiply(Matrix other) {
    if (getCols() != other.getRows()) {
      throw new IllegalArgumentException(
          String.format(
              "Number of rows must be equal to the number of columns in the source array (%d)",
              getCols()));
    }

    Matrix result = new Matrix(getRows(), other.getCols());

    for (int y = 0; y < getCols(); y++) {
      for (int x = 0; x < other.getCols(); x++) {
        double sum = 0;
        for (int k = 0; k < getCols(); k++) {
          sum += get(y, k) * other.get(k, x);
        }
        result.set(y, x, sum);
      }
    }

    return result;
  }

  public Matrix divide(Matrix other) {
    return multiply(other.inverse());
  }

  public double frobenius() {
    return Arrays.stream(data).reduce(0, (sum, value) -> sum + Math.pow(value, 2));
  }
}
