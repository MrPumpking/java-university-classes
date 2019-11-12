package com.github.mrpumpking.lab6;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) {
    try {
      CSVReader reader =
          new CSVReader(
              Paths.get("src", "main", "resources", "with-header.csv").toString(), ";", true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
