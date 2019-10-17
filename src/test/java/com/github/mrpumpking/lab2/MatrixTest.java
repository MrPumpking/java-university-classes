package com.github.mrpumpking.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MatrixTest {
  private Matrix matrix;
  private static final double COMPARATOR_PRECISION = 0.0001;

  @BeforeEach
  public void setUp() {
    this.matrix = new Matrix(new double[][] {{1, 2}, {3, 4}});
  }

  @Test
  public void initEmpty() {
    Matrix matrix = new Matrix(2, 2);
    assertThat(matrix.getRows()).isEqualTo(2);
    assertThat(matrix.getCols()).isEqualTo(2);
    assertThat(matrix.getData()).hasSize(4);
    assertThat(matrix.getData()).containsOnly(0, 0, 0, 0);
  }

  @Test
  public void initFromData() {
    assertThat(matrix.getRows()).isEqualTo(2);
    assertThat(matrix.getCols()).isEqualTo(2);
    assertThat(matrix.getData()).hasSize(4);
    assertThat(matrix.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1, 2, 3, 4});
  }

  @Test
  public void initFromMatrix() {
    Matrix other = new Matrix(matrix);
    assertThat(other.getRows()).isEqualTo(matrix.getRows());
    assertThat(other.getCols()).isEqualTo(matrix.getCols());
    assertThat(other.getData()).hasSize(matrix.getData().length);
    assertThat(other.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(matrix.getData());
  }

  @Test
  public void copyFromMatrixArrayOfDifferentSizedRows() {
    double[][] data = new double[][] {{1}, {3, 4}};
    Matrix matrix = new Matrix(data);
    assertThat(matrix.get(0, 1)).isEqualTo(0);
  }

  @Test
  public void getByIndex() {
    assertThat(matrix.get(0)).isEqualTo(1);
  }

  @Test
  public void getByCoords() {
    assertThat(matrix.get(0, 0)).isEqualTo(1);
  }

  @Test
  public void setByIndex() {
    matrix.set(0, 10);
    assertThat(matrix.get(0)).isEqualTo(10);
  }

  @Test
  public void setByCoords() {
    matrix.set(0, 0, 10);
    assertThat(matrix.get(0)).isEqualTo(10);
  }

  @Test
  public void getRows() {
    assertThat(matrix.getRows()).isEqualTo(matrix.asArray().length);
  }

  @Test
  public void getCols() {
    assertThat(matrix.getCols()).isEqualTo(matrix.asArray()[0].length);
  }

  @Test
  public void getShape() {
    assertThat(matrix.getShape()).isEqualTo(new int[] {matrix.getRows(), matrix.getCols()});
  }

  @Test
  public void asArray() {
    assertThat(matrix.asArray()).isEqualTo(new double[][] {{1, 2}, {3, 4}});
  }

  @Test
  public void asString() {
    assertThat(matrix.toString()).isEqualTo("[[1.0,2.0][3.0,4.0]]");
  }

  @Test
  public void reshape() {
    Matrix matrix = new Matrix(2, 3);
    matrix.reshape(3, 2);
    assertThat(matrix.getRows()).isEqualTo(3);
    assertThat(matrix.getCols()).isEqualTo(2);
  }

  @Test()
  public void reshapeWithInvalidSizes() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> {
              matrix.reshape(4, 4);
            })
        .withMessage(
            "%d x %d matrix cannot be reshaped to %d x %d",
            matrix.getRows(), matrix.getCols(), 4, 4);
  }

  @Test
  public void processEachValue() {
    Matrix other = new Matrix(new double[][] {{1, 1}, {1, 1}});
    Matrix result = matrix.processEachValue(other, Double::sum);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 3, 4, 5});
  }

  @Test
  public void processEachValueWithMatricesOfDifferentSizes() {
    Matrix other = new Matrix(1, 1);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> {
              matrix.processEachValue(other, Double::sum);
            })
        .withMessage(
            "Expected second matrix to be [%d x %d] but got [%d x %d]",
            matrix.getRows(), matrix.getCols(), 1, 1);
  }

  @Test
  public void processEachValueUsingScalar() {
    Matrix result = matrix.processEachValueUsingScalar(1, Double::sum);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 3, 4, 5});
  }

  @Test
  public void addScalar() {
    Matrix result = matrix.add(1);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 3, 4, 5});
  }

  @Test
  public void subtractScalar() {
    Matrix result = matrix.subtract(1);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {0, 1, 2, 3});
  }

  @Test
  public void multiplyByScalar() {
    Matrix result = matrix.multiply(2);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 4, 6, 8});
  }

  @Test
  public void divideByScalar() {
    Matrix result = matrix.divide(2);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1D / 2, 1, 3D / 2, 2});
  }

  @Test
  public void addMatrix() {
    Matrix result = matrix.add(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 4, 6, 8});
  }

  @Test
  public void subtractMatrix() {
    Matrix result = matrix.subtract(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {0, 0, 0, 0});
  }

  @Test
  public void minor() {
    Matrix minor = matrix.minor(0, 0);
    assertThat(minor.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {4});
  }

  @Test
  public void determinant() {
    assertThat(matrix.determinant()).isEqualTo(-2);
  }

  @Test
  public void determinantWithNonSquareArray() {
    Matrix matrix =
        new Matrix(
            new double[][] {
              {1, 2, 3},
              {4, 5, 6}
            });
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(matrix::determinant)
        .withMessage("Cannot calculate the determinant of non-square matrix");
  }

  @Test
  public void inverse() {
    Matrix inverse = matrix.inverse();
    assertThat(inverse.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {-2, 1, 3D / 2, -1D / 2});
  }

  @Test
  public void inverse4By4() {
    Matrix inverse =
        new Matrix(
                new double[][] {
                  {0, 0, 0, 1},
                  {0, 0, 2, 0},
                  {0, 3, 0, 0},
                  {4, 0, 0, 0}
                })
            .inverse();

    assertThat(inverse.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(
            new double[] {
              0, 0, 0, 1D / 4,
              0, 0, 1D / 3, 0,
              0, 1D / 2, 0, 0,
              1, 0, 0, 0
            });
  }

  @Test
  public void inverseOfMatrixWithZeroDeterminant() {
    Matrix matrix = new Matrix(2, 2);
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(matrix::inverse)
        .withMessage("Cannot calculate inverse of matrix with 0 determinant");
  }

  @Test
  public void transpose() {
    Matrix transposed = matrix.transpose();
    assertThat(transposed.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1, 3, 2, 4});
  }

  @Test
  public void multiply() {
    Matrix result = matrix.multiply(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {7, 10, 15, 22});
  }

  @Test
  public void divide() {
    Matrix result = matrix.divide(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1, 0, 0, 1});
  }

  @Test
  public void multiplyMatricesWithIncompatibleSizes() {
    Matrix other = new Matrix(3, 4);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> matrix.multiply(other))
        .withMessage(
            "Number of rows must be equal to the number of columns in the source array (%d)",
            matrix.getCols());
  }

  @Test
  public void frobenius() {
    assertThat(matrix.frobenius()).usingComparator(Double::compareTo).isEqualTo(30);
  }
}
