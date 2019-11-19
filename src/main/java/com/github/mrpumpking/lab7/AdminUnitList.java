package com.github.mrpumpking.lab7;

import com.github.mrpumpking.lab6.CSVReader;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUnitList {
  List<AdminUnit> units = new ArrayList<>();
  private CSVReader reader;

  public void read(String filePath) throws IOException, ColumnNotFoundException {
    reader = new CSVReader(filePath, ",", true);

    while (reader.next()) {
      AdminUnit unit = new AdminUnit();
      BoundingBox bbox = new BoundingBox();

      unit.name = reader.get("name");
      unit.adminLevel = reader.getInt("admin_level", 0);
      unit.population = reader.getDouble("population", 0);
      unit.area = reader.getDouble("area", 0);
      unit.density = reader.getDouble("density", 0);

      bbox.xMin = reader.getDouble("x1", 0);
      bbox.xMax = reader.getDouble("x3", 0);
      bbox.yMin = reader.getDouble("y1", 0);
      bbox.yMax = reader.getDouble("y2", 0);

      unit.bbox = bbox;
      units.add(unit);
    }
  }

  public void list(PrintStream out) {
    units.forEach(out::println);
  }

  public void list(PrintStream out, int offset, int limit) {
    units.stream().skip(offset).limit(limit).forEach(out::println);
  }

  public AdminUnitList selectByName(String pattern, boolean regex) {
    AdminUnitList result = new AdminUnitList();

    result.units =
        units.stream()
            .filter(unit -> regex ? unit.name.matches(pattern) : unit.name.contains(pattern))
            .collect(Collectors.toList());

    return result;
  }
}
