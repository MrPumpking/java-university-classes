package com.github.mrpumpking.lab7;

import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;
import java.util.function.Predicate;

public class Main {

  public static void main(String[] args) {
    try {
      new Main();
    } catch (IOException | ColumnNotFoundException e) {
      e.printStackTrace();
    }
  }

  public Main() throws IOException, ColumnNotFoundException {
    ExecutionTimer timer = new ExecutionTimer();
    AdminUnitList list = new AdminUnitList();
    String filePath = getClass().getResource("/admin-units.csv").getPath();
    list.read(filePath);

    AdminUnit unit = list.selectByName("^Kraków$", true).units.get(0);

    System.out.println("=== Starting linear search ===");
    timer.start();
    AdminUnitList list1 = list.getNeighbours(unit, 10);
    timer.stop();
    long time1 = timer.getExecutionTime();
    System.out.println("=== Linear time: " + time1 / 1000 + "ms");
    timer.reset();

    System.out.println();

    System.out.println("=== Starting R-Tree search ===");
    timer.start();
    AdminUnitList list2 = list.getNeighbours(unit, 10);
    timer.stop();
    long time2 = timer.getExecutionTime();
    System.out.println("=== R-Tree time: " + time2 / 1000 + "ms");

    System.out.println();
    System.out.println("=== Difference: " + Math.abs(time1 - time2) / 1000 + "ms");

    System.out.println();
    System.out.println("=== Results:");
    list2.list(System.out);

    System.out.println("=== Filtering");
    Predicate<AdminUnit> predicateStartingWithK = item -> item.name.startsWith("K");
    AdminUnitList unitsStartingWithK = list.filter(predicateStartingWithK);
    AdminUnitList unitsNotStartingWithK = list.filter(predicateStartingWithK.negate());
    AdminUnitList districts =
        list.filter(
            item ->
                item.adminLevel == 6
                    && item.parent != null
                    && item.parent.name.equals("województwo małopolskie"));
    //    unitsStartingWithK.list(System.out)

    AdminUnitQuery query =
        new AdminUnitQuery()
            .selectFrom(list)
            .where(item -> item.area > 1000)
            .or(item -> item.name.startsWith("Sz"))
            .sort((a, b) -> Double.compare(a.area, b.area))
            .limit(100);

    query.execute().list(System.out);
  }
}
