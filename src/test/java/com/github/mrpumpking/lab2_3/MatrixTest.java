package com.github.mrpumpking.lab2_3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MatrixTest {
  private Matrix matrix;
  private static final double COMPARATOR_PRECISION = 1e-10;

  @BeforeEach
  void setUp() {
    this.matrix = new Matrix(new double[][] {{1, 2}, {3, 4}});
  }

  @Test
  void givenMatrixDimensions_whenMatrixInitialise_thenCreateMatrixOfGivenSizeFilledWithZeros() {
    Matrix matrix = new Matrix(2, 2);
    assertThat(matrix.getRows()).isEqualTo(2);
    assertThat(matrix.getCols()).isEqualTo(2);
    assertThat(matrix.getData()).containsOnly(0, 0, 0, 0);
  }

  @Test
  void givenArrayOfNumbers_whenMatrixInitialise_thenCreateMatrixWithSizeAndDataMatchingTheArray() {
    assertThat(matrix.getRows()).isEqualTo(2);
    assertThat(matrix.getCols()).isEqualTo(2);
    assertThat(matrix.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1, 2, 3, 4});
  }

  @Test
  void givenMatrixInstance_whenMatrixInitialise_thenCopyDataFromGivenMatrixAndCreateNewOne() {
    Matrix other = new Matrix(matrix);
    assertThat(other.getRows()).isEqualTo(matrix.getRows());
    assertThat(other.getCols()).isEqualTo(matrix.getCols());
    assertThat(other.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(matrix.getData());
  }

  @Test
  void givenSize_whenGenerateRandomMatrix_thenCreateMatrixOfGivenSizeFilledWithRandomValues() {
    Matrix matrix = Matrix.random(3, 3);
    assertThat(matrix.getRows()).isEqualTo(3);
    assertThat(matrix.getCols()).isEqualTo(3);
    assertThat(matrix.getData().length).isEqualTo(3 * 3);
  }

  @Test
  void givenSize_whenGenerateEyeMatrix_thenCreateEyeMatrixOfGivenSizesWith1OnDiagonal() {
    Matrix matrix = Matrix.eye(3);
    assertThat(matrix.getData()).containsExactly(1, 0, 0, 0, 1, 0, 0, 0, 1);
  }

  @Test
  void given2DArrayWithNumbers_whenCopy_thenCopyNumbersToCorrespondingIndexesIn1DArray() {
    double[][] data = new double[][] {{1}, {3, 4}};
    Matrix matrix = new Matrix(data);
    assertThat(matrix.get(0, 1)).isEqualTo(0);
  }

  @Test
  void givenIndex_whenGetByIndex_thenReturnValueFromCorrespondingIndex() {
    assertThat(matrix.get(0)).isEqualTo(1);
  }

  @Test
  void givenRowAndColumn_whenGetByCoordinates_thenReturnValueFromCorrespondingCoordinates() {
    assertThat(matrix.get(0, 0)).isEqualTo(1);
  }

  @Test
  void givenIndexAndValue_whenSetByIndex_thenSetValueAtGivenIndex() {
    matrix.set(0, 10);
    assertThat(matrix.get(0)).isEqualTo(10);
  }

  @Test
  void givenRowColumnAndValue_whenSetByCoordinates_thenSetValueAtGivenCoordinates() {
    matrix.set(0, 0, 10);
    assertThat(matrix.get(0)).isEqualTo(10);
  }

  @Test
  void whenGetRows_thenReturnRowsCount() {
    assertThat(matrix.getRows()).isEqualTo(matrix.asArray().length);
  }

  @Test
  void whenGetCols_thenReturnColsCount() {
    assertThat(matrix.getCols()).isEqualTo(matrix.asArray()[0].length);
  }

  @Test
  void whenGetShape_thenReturnArrayContainingRowsAndCols() {
    assertThat(matrix.getShape()).isEqualTo(new int[] {matrix.getRows(), matrix.getCols()});
  }

  @Test
  void whenGetAsArray_thenReturn2DArrayContainingMatrixValues() {
    assertThat(matrix.asArray()).isEqualTo(new double[][] {{1, 2}, {3, 4}});
  }

  @Test
  void whenGetAsString_thenReturnMatrixStringWithCorrectFormat() {
    assertThat(matrix.toString()).isEqualTo("[[1.0,2.0][3.0,4.0]]");
  }

  @Test
  void givenNewRowsAndCols_whenReshape_thenSetMatrixRowsAndColsToNewValues() {
    Matrix matrix = new Matrix(2, 3);
    matrix.reshape(3, 2);
    assertThat(matrix.getRows()).isEqualTo(3);
    assertThat(matrix.getCols()).isEqualTo(2);
  }

  @Test()
  void givenInvalidRowsOrCols_whenReshape_thenThrowException() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> matrix.reshape(4, 4))
        .withMessage(
            "%d x %d matrix cannot be reshaped to %d x %d",
            matrix.getRows(), matrix.getCols(), 4, 4);
  }

  @Test
  void
      givenScalarAndFunction_whenApplyToEachValue_thenApplyGivenFunctionToEveryMatrixValueWithScalarAsArgument() {
    Matrix result = matrix.applyToEachValue(1, Double::sum);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 3, 4, 5});
  }

  @Test
  void givenScalar_whenAdd_thenAddScalarValueToEachMatrixValue() {
    Matrix result = matrix.add(1);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 3, 4, 5});
  }

  @Test
  void givenScalar_whenSubtract_thenSubtractScalarValueFromEachMatrixValue() {
    Matrix result = matrix.subtract(1);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {0, 1, 2, 3});
  }

  @Test
  void givenScalar_whenMultiply_thenMultiplyEachMatrixValueByScalarValue() {
    Matrix result = matrix.multiply(2);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 4, 6, 8});
  }

  @Test
  void givenScalar_whenDivide_thenDivideEachMatrixValueByScalarValue() {
    Matrix result = matrix.divide(2);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1D / 2, 1, 3D / 2, 2});
  }

  @Test
  void
      givenMatrixOfTheSameSizeAndFunction_whenApplyToCorrespondingValues_thenApplyGivenFunctionWithMatchingMatrixElementsAsArguments() {
    Matrix other = new Matrix(new double[][] {{1, 1}, {1, 1}});
    Matrix result = matrix.applyToCorrespondingValues(other, Double::sum);
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 3, 4, 5});
  }

  @Test
  void givenMatrixOfDifferentSize_whenApplyToCorrespondingValues_thenThrowException() {
    Matrix other = new Matrix(1, 1);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> matrix.applyToCorrespondingValues(other, Double::sum))
        .withMessage(
            "Expected second matrix to be [%d x %d] but got [%d x %d]",
            matrix.getRows(), matrix.getCols(), 1, 1);
  }

  @Test
  void givenMatrixOfTheSameSize_whenAdd_thenAddCorrespondingMatrixValues() {
    Matrix result = matrix.add(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {2, 4, 6, 8});
  }

  @Test
  void givenMatrixOfTheSameSize_whenSubtract_thenReturnSubtractCorrespondingMatrixValues() {
    Matrix result = matrix.subtract(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {0, 0, 0, 0});
  }

  @Test
  void givenMatrixWithMatchingNumberOfRows_whenMultiply_thenPerformMatrixMultiplication() {
    Matrix result = matrix.multiply(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {7, 10, 15, 22});
  }

  @Test
  void givenMatrixWithMatchingNumberOfRows_whenDivide_thenPerformMatrixDivision() {
    Matrix result = matrix.divide(new Matrix(matrix));
    assertThat(result.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1, 0, 0, 1});
  }

  @Test
  void givenMatrixWithNotMatchingNumberOfRows_whenMultiply_thenThrowException() {
    Matrix other = new Matrix(3, 4);
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> matrix.multiply(other))
        .withMessage(
            "Number of rows must be equal to the number of columns in the source array (%d)",
            matrix.getCols());
  }

  @Test
  void givenRowAndCol_whenGetMinor_thenReturnMatrixMinorAtGivenCoordinates() {
    Matrix minor = matrix.minor(0, 0);
    assertThat(minor.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {4});
  }

  @Test
  void givenSquareMatrix_whenGetDeterminant_thenReturnMatrixDeterminant() {
    assertThat(matrix.determinant()).isEqualTo(-2);
  }

  @Test
  void givenNonSquareMatrix_whenGetDeterminant_thenThrowException() {
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
  void givenMatrixWithNonZeroDeterminant_whenGetInverse_thenReturnMatrixInverse() {
    Matrix inverse = matrix.inverse();
    assertThat(inverse.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {-2, 1, 3D / 2, -1D / 2});
  }

  @Test
  void given4By4MatrixWithNonZeroDeterminant_whenGetInverse_thenReturnMatrixInverse() {
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
  void givenMatrixWithZeroDeterminant_whenGetInverse_thenThrowException() {
    Matrix matrix = new Matrix(2, 2);
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(matrix::inverse)
        .withMessage("Cannot calculate inverse of matrix with 0 determinant");
  }

  @Test
  void whenTranspose_thenReturnTransposedMatrix() {
    Matrix transposed = matrix.transpose();
    assertThat(transposed.getData())
        .usingComparatorWithPrecision(COMPARATOR_PRECISION)
        .isEqualTo(new double[] {1, 3, 2, 4});
  }

  @Test
  void whenGetFrobenius_thenReturnSumOfEachValueSquared() {
    assertThat(matrix.frobenius()).usingComparator(Double::compareTo).isEqualTo(30);
  }
}
