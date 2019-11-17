package com.github.mrpumpking.lab6;

import com.github.mrpumpking.lab6.exceptions.ColumnIndexOutOfBounds;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;
import com.google.inject.internal.util.Lists;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader implements Closeable {
  private String delimiter;
  private String[] current;
  private int currentLength;
  private boolean hasHeader;
  private BufferedReader reader;
  private Map<String, Integer> columnLabelToIndex;

  private static final String DEFAULT_DELIMITER = ",";
  private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
  private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
  private static final String DEFAULT_DATE_TIME_FORMAT =
      DEFAULT_TIME_FORMAT + " " + DEFAULT_DATE_FORMAT;
  private static final String SPLIT_REGEX = "%s(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

  public CSVReader(String filePath) throws IOException {
    this(filePath, DEFAULT_DELIMITER, false);
  }

  public CSVReader(String filePath, String delimiter) throws IOException {
    this(filePath, delimiter, false);
  }

  public CSVReader(String filePath, String delimiter, boolean hasHeader) throws IOException {
    this(new FileReader(filePath), delimiter, hasHeader);
  }

  public CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
    this.columnLabelToIndex = new HashMap<>();
    this.reader = new BufferedReader(reader);
    this.delimiter = buildDelimiterRegex(delimiter);
    this.hasHeader = hasHeader;

    if (hasHeader) {
      parseHeader();
    }
  }

  public boolean next() throws IOException {
    String line = reader.readLine();

    if (line == null) {
      return false;
    }

    current = separate(line);
    currentLength = line.length();
    return true;
  }

  public String get(int index) throws ColumnIndexOutOfBounds {
    try {
      return current[index];
    } catch (IndexOutOfBoundsException exception) {
      throw new ColumnIndexOutOfBounds(index, current.length);
    }
  }

  public String get(String column) throws ColumnNotFoundException {
    if (!columnLabelToIndex.containsKey(column)) {
      throw new ColumnNotFoundException(column);
    }
    return current[columnLabelToIndex.get(column)];
  }

  public int getInt(int index) throws ColumnIndexOutOfBounds {
    return Integer.parseInt(get(index));
  }

  public int getInt(String column) throws ColumnNotFoundException {
    return Integer.parseInt(get(column));
  }

  public double getDouble(int index) throws ColumnIndexOutOfBounds {
    return Double.parseDouble(get(index));
  }

  public double getDouble(String column) throws ColumnNotFoundException {
    return Double.parseDouble(get(column));
  }

  public long getLong(int index) throws ColumnIndexOutOfBounds {
    return Long.parseLong(get(index));
  }

  public long getLong(String column) throws ColumnNotFoundException {
    return Long.parseLong(get(column));
  }

  public LocalTime getTime(int index) throws ColumnIndexOutOfBounds {
    return getTime(index, DEFAULT_TIME_FORMAT);
  }

  public LocalTime getTime(int index, String format) throws ColumnIndexOutOfBounds {
    return LocalTime.parse(get(index), DateTimeFormatter.ofPattern(format));
  }

  public LocalTime getTime(String column) throws ColumnNotFoundException {
    return getTime(column, DEFAULT_TIME_FORMAT);
  }

  public LocalTime getTime(String column, String format) throws ColumnNotFoundException {
    return LocalTime.parse(get(column), DateTimeFormatter.ofPattern(format));
  }

  public LocalDate getDate(int index) throws ColumnIndexOutOfBounds {
    return getDate(index, DEFAULT_DATE_FORMAT);
  }

  public LocalDate getDate(int index, String format) throws ColumnIndexOutOfBounds {
    return LocalDate.parse(get(index), DateTimeFormatter.ofPattern(format));
  }

  public LocalDate getDate(String column) throws ColumnNotFoundException {
    return getDate(column, DEFAULT_DATE_FORMAT);
  }

  public LocalDate getDate(String column, String format) throws ColumnNotFoundException {
    return LocalDate.parse(get(column), DateTimeFormatter.ofPattern(format));
  }

  public LocalDateTime getDateTime(int index) throws ColumnIndexOutOfBounds {
    return getDateTime(index, DEFAULT_DATE_TIME_FORMAT);
  }

  public LocalDateTime getDateTime(int index, String format) throws ColumnIndexOutOfBounds {
    return LocalDateTime.parse(get(index), DateTimeFormatter.ofPattern(format));
  }

  public LocalDateTime getDateTime(String column) throws ColumnNotFoundException {
    return getDateTime(column, DEFAULT_DATE_TIME_FORMAT);
  }

  public LocalDateTime getDateTime(String column, String format) throws ColumnNotFoundException {
    return LocalDateTime.parse(get(column), DateTimeFormatter.ofPattern(format));
  }

  public List<String> getColumnLabels() {
    return Lists.newArrayList(columnLabelToIndex.keySet());
  }

  public int getRecordLength() {
    return currentLength;
  }

  public boolean isMissing(int index) {
    try {
      return get(index).isEmpty();
    } catch (ColumnIndexOutOfBounds exception) {
      return true;
    }
  }

  public boolean isMissing(String column) {
    try {
      return get(column).isEmpty();
    } catch (ColumnNotFoundException exception) {
      return true;
    }
  }

  public boolean hasHeader() {
    return hasHeader;
  }

  @Override
  public void close() throws IOException {
    reader.close();
  }

  private String buildDelimiterRegex(String delimiter) {
    return String.format(SPLIT_REGEX, delimiter);
  }

  private String[] separate(String line) {
    return line.split(delimiter);
  }

  private void parseHeader() throws IOException {
    if (!next()) {
      return;
    }

    for (int i = 0; i < current.length; i++) {
      columnLabelToIndex.put(current[i], i);
    }
  }
}
