package com.github.mrpumpking.lab6;

import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    String filePath = getClass().getResource("/with-header.csv").getPath();

    try {
      CSVReader reader = new CSVReader(filePath, ";", true);
      reader.next();

      System.out.println(reader.getColumnLabels());
      System.out.println(reader.get("nazwisko"));

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ColumnNotFoundException e) {
      e.printStackTrace();
    }
  }
}
