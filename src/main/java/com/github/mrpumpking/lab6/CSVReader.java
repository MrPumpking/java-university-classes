package com.github.mrpumpking.lab6;

import com.github.mrpumpking.lab6.exceptions.ColumnIndexOutOfBounds;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {
  private String delimiter;
  private String[] current;
  private boolean hasHeader;
  private BufferedReader reader;
  private Map<String, Integer> columnLabelToIndex;

  public CSVReader(String filePath) throws IOException {
    this(filePath, ",", false);
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
    this.delimiter = delimiter;
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

  public void close() throws IOException {
    reader.close();
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
