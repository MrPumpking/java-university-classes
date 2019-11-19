package com.github.mrpumpking.lab7;

import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    AdminUnitList list = new AdminUnitList();
    String filePath = getClass().getResource("/admin-units.csv").getPath();

    try {
      list.read(filePath);
      list.list(System.out, 0, 10);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (ColumnNotFoundException e) {
      e.printStackTrace();
    }
  }
}
