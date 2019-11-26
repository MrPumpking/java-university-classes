package com.github.mrpumpking.lab7;

public class Main {

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    //    AdminUnitList list = new AdminUnitList();
    //    String filePath = getClass().getResource("/admin-units.csv").getPath();
    //
    //    try {
    //      list.read(filePath);
    //      list.list(System.out, 0, 1);
    //      System.out.println(list.get(0).children);
    //
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    } catch (ColumnNotFoundException e) {
    //      e.printStackTrace();
    //    }

    BoundingBox box = new BoundingBox();
    box.xMin = box.xMax = box.yMin = box.yMax = Double.NaN;
    box.addPoint(10, 10);

    System.out.println(box);
  }
}
