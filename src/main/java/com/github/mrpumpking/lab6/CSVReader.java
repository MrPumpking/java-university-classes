package com.github.mrpumpking.lab6;

import com.github.mrpumpking.lab6.exceptions.ColumnIndexOutOfBoundsException;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;
import com.google.inject.internal.util.Lists;

import java.io.*;
import java.nio.charset.Charset;
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
  private static final String DEFAULT_ENCODING = "UTF-8";
  private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
  private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
  private static final String DEFAULT_DATE_TIME_FORMAT =
      DEFAULT_TIME_FORMAT + " " + DEFAULT_DATE_FORMAT;
  private static final String SPLIT_REGEX = "%s(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

  public CSVReader(String filePath) throws IOException {
    this(filePath, DEFAULT_DELIMITER, false, DEFAULT_ENCODING);
  }

  public CSVReader(String filePath, String delimiter) throws IOException {
    this(filePath, delimiter, false, DEFAULT_ENCODING);
  }

  public CSVReader(String filePath, String delimiter, boolean hasHeader) throws IOException {
    this(filePath, delimiter, hasHeader, DEFAULT_ENCODING);
  }

  public CSVReader(String filePath, String delimiter, boolean hasHeader, String encoding)
      throws IOException {
    this(
        new InputStreamReader(new FileInputStream(filePath), Charset.forName(encoding)),
        delimiter,
        hasHeader);
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

  public String get(int index) throws ColumnIndexOutOfBoundsException {
    try {
      return current[index];
    } catch (ArrayIndexOutOfBoundsException exception) {
      throw new ColumnIndexOutOfBoundsException(index, current.length);
    }
  }

  public String get(int index, String defaultValue) throws ColumnIndexOutOfBoundsException {
    return isMissing(index) ? defaultValue : get(index);
  }

  public String get(String column) throws ColumnNotFoundException {
    if (!columnLabelToIndex.containsKey(column)) {
      throw new ColumnNotFoundException(column);
    }
    return current[columnLabelToIndex.get(column)];
  }

  public String get(String column, String defaultValue) throws ColumnNotFoundException {
    return isMissing(column) ? defaultValue : get(column);
  }

  public int getInt(int index) throws ColumnIndexOutOfBoundsException {
    return Integer.parseInt(get(index));
  }

  public int getInt(int index, int defaultValue) throws ColumnIndexOutOfBoundsException {
    return isMissing(index) ? defaultValue : getInt(index);
  }

  public int getInt(String column) throws ColumnNotFoundException {
    return Integer.parseInt(get(column));
  }

  public int getInt(String column, int defaultValue) throws ColumnNotFoundException {
    return isMissing(column) ? defaultValue : getInt(column);
  }

  public double getDouble(int index) throws ColumnIndexOutOfBoundsException {
    return Double.parseDouble(get(index));
  }

  public double getDouble(int index, double defaultValue) throws ColumnIndexOutOfBoundsException {
    return isMissing(index) ? defaultValue : getDouble(index);
  }

  public double getDouble(String column) throws ColumnNotFoundException {
    return Double.parseDouble(get(column));
  }

  public double getDouble(String column, double defaultValue) throws ColumnNotFoundException {
    return isMissing(column) ? defaultValue : getDouble(column);
  }

  public long getLong(int index) throws ColumnIndexOutOfBoundsException {
    return Long.parseLong(get(index));
  }

  public long getLong(int index, long defaultValue) throws ColumnIndexOutOfBoundsException {
    return isMissing(index) ? defaultValue : getLong(index);
  }

  public long getLong(String column) throws ColumnNotFoundException {
    return Long.parseLong(get(column));
  }

  public long getLong(String column, long defaultValue) throws ColumnNotFoundException {
    return isMissing(column) ? defaultValue : getLong(column);
  }

  public LocalTime getTime(int index) throws ColumnIndexOutOfBoundsException {
    return getTime(index, DEFAULT_TIME_FORMAT);
  }

  public LocalTime getTime(int index, String format) throws ColumnIndexOutOfBoundsException {
    return LocalTime.parse(get(index), DateTimeFormatter.ofPattern(format));
  }

  public LocalTime getTime(String column) throws ColumnNotFoundException {
    return getTime(column, DEFAULT_TIME_FORMAT);
  }

  public LocalTime getTime(String column, String format) throws ColumnNotFoundException {
    return LocalTime.parse(get(column), DateTimeFormatter.ofPattern(format));
  }

  public LocalDate getDate(int index) throws ColumnIndexOutOfBoundsException {
    return getDate(index, DEFAULT_DATE_FORMAT);
  }

  public LocalDate getDate(int index, String format) throws ColumnIndexOutOfBoundsException {
    return LocalDate.parse(get(index), DateTimeFormatter.ofPattern(format));
  }

  public LocalDate getDate(String column) throws ColumnNotFoundException {
    return getDate(column, DEFAULT_DATE_FORMAT);
  }

  public LocalDate getDate(String column, String format) throws ColumnNotFoundException {
    return LocalDate.parse(get(column), DateTimeFormatter.ofPattern(format));
  }

  public LocalDateTime getDateTime(int index) throws ColumnIndexOutOfBoundsException {
    return getDateTime(index, DEFAULT_DATE_TIME_FORMAT);
  }

  public LocalDateTime getDateTime(int index, String format)
      throws ColumnIndexOutOfBoundsException {
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
    return index < 0 || index >= current.length || current[index].isEmpty();
  }

  public boolean isMissing(String column) {
    return isMissing(columnLabelToIndex.getOrDefault(column, -1));
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
