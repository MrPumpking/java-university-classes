package com.github.mrpumpking.lab6;

import com.github.mrpumpking.lab6.exceptions.ColumnIndexOutOfBounds;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CSVReaderTest {

  private CSVReader reader;

  @BeforeEach
  void setUp() throws IOException {
    String filePath = getClass().getResource("/with-header.csv").getPath();
    reader = new CSVReader(filePath, ",", true);
  }

  @AfterEach
  void tearDown() throws IOException {
    reader.close();
  }

  @Test
  void givenCSVWithHeader_whenGetColumnLabels_thenReturnListOfHeaderColumns() {
    List<String> columns = reader.getColumnLabels();
    assertThat(columns)
        .containsExactlyInAnyOrder("imie", "nazwisko", "wiek", "konto", "data rejestracji");
  }

  @Test
  void givenReader_whenReadWhileNext_thenReadAndParseEachLine()
      throws IOException, ColumnNotFoundException {
    List<String> names = new LinkedList<>();

    while (reader.next()) {
      names.add(reader.get("imie"));

      /*
      System.out.println(reader.get("imie"));
      System.out.println(reader.get("nazwisko"));
      System.out.println(reader.get("wiek"));
      System.out.println(reader.get("konto"));
      System.out.println(reader.get("data rejestracji"));
      */
    }

    assertThat(names).containsExactly("Jan", "Adam", "Justyna");
  }

  @Test
  void givenColumnName_whenGetSpecificType_thenReturnParsedValueOfThatType()
      throws IOException, ColumnNotFoundException {
    reader.next();

    assertThat(reader.getInt("wiek")).isInstanceOf(Integer.class).isEqualTo(30);
    assertThat(reader.getDouble("konto")).isInstanceOf(Double.class).isEqualTo(4200.80);
    assertThat(reader.getDate("data rejestracji"))
        .isInstanceOf(LocalDate.class)
        .isEqualTo(LocalDate.of(2019, 11, 15));
  }

  @Test
  void givenUnknownColumnName_whenGetValueByColumn_thenThrowException() throws IOException {
    reader.next();
    String column = "unknown";

    assertThatExceptionOfType(ColumnNotFoundException.class)
        .isThrownBy(() -> reader.get(column))
        .withMessage(String.format("Column \"%s\" not found", column));
  }

  @Test
  void givenInvalidColumnIndex_whenGetValueByIndex_thenThrowException() throws IOException {
    reader.next();
    int index = 10;
    int columnCount = 5;

    assertThatExceptionOfType(ColumnIndexOutOfBounds.class)
        .isThrownBy(() -> reader.get(index))
        .withMessage(
            String.format(
                "Column index must be within [0-%d]. Recieved index = %d", columnCount, index));
  }

  @Test
  void givenColumnName_whenValueIsEmpty_thenReturnEmptyString()
      throws IOException, ColumnNotFoundException {
    reader.next();
    reader.next();
    reader.next();

    assertThat(reader.get("wiek")).isEmpty();
  }

  @Test
  void givenReader_whenInitialised_thenParseStringAsCSV()
      throws IOException, ColumnIndexOutOfBounds {
    String text = "a,b,c\n123.4,567.8,91011.12";
    reader = new CSVReader(new StringReader(text), ",", true);
    reader.next();

    assertThat(reader.getColumnLabels()).containsExactlyInAnyOrder("a", "b", "c");
    assertThat(reader.getDouble(0)).isEqualTo(123.4);

    reader.close();
  }

  @Test
  void givenFileWithNoHeader_whenInitialise_thenParseCSV()
      throws IOException, ColumnIndexOutOfBounds {
    String filePath = getClass().getResource("/without-header.csv").getPath();
    CSVReader reader = new CSVReader(filePath, ",", false);

    reader.next();
    assertThat(reader.get(0)).isEqualTo("Jan");

    reader.close();
  }

  @Test
  void givenFileWithFieldsContainingDelimiter_whenParseAndGet_thenReturnCorrectValues()
      throws IOException, ColumnNotFoundException {
    String filePath = getClass().getResource("/titanic.csv").getPath();
    CSVReader reader = new CSVReader(filePath, ",", true);

    reader.next();
    assertThat(reader.get("Name")).isEqualTo("\"Braund, Mr. Owen Harris\"");

    reader.close();
  }
}
