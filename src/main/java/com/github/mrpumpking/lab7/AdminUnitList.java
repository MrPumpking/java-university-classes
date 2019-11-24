package com.github.mrpumpking.lab7;

import com.github.mrpumpking.lab6.CSVReader;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminUnitList {
  private CSVReader reader;
  List<AdminUnit> units = new ArrayList<>();
  Map<Long, AdminUnit> idToAdminUnit = new HashMap<>();
  Map<AdminUnit, Long> adminUnitToParentId = new HashMap<>();
  Map<Long, List<AdminUnit>> parentIdToChildren = new HashMap<>();

  public void read(String filePath) throws IOException, ColumnNotFoundException {
    reader = new CSVReader(filePath, ",", true);
    extractAllAdminUnits();
    setUnitRelationValues();
    fixMissingValues();
  }

  public void list(PrintStream out) {
    units.forEach(out::println);
  }

  public void list(PrintStream out, int offset, int limit) {
    units.stream().skip(offset).limit(limit).forEach(out::println);
  }

  public AdminUnit get(int index) {
    return units.get(index);
  }

  public AdminUnitList selectByName(String pattern, boolean regex) {
    AdminUnitList result = new AdminUnitList();

    result.units =
        units.stream()
            .filter(unit -> regex ? unit.name.matches(pattern) : unit.name.contains(pattern))
            .collect(Collectors.toList());

    return result;
  }

  private void extractAllAdminUnits() throws IOException, ColumnNotFoundException {
    while (reader.next()) {
      AdminUnit unit = extractCurrentAdminUnit();

      units.add(unit);
      idToAdminUnit.put(reader.getLong("id"), unit);

      long parentId = reader.getLong("parent", -1);
      adminUnitToParentId.put(unit, parentId);

      if (parentId != -1) {
        if (parentIdToChildren.containsKey(parentId)) {
          parentIdToChildren.get(parentId).add(unit);
        } else {
          List<AdminUnit> children = new ArrayList<>();
          children.add(unit);
          parentIdToChildren.put(parentId, children);
        }
      }
    }
  }

  private AdminUnit extractCurrentAdminUnit() throws ColumnNotFoundException {
    AdminUnit unit = new AdminUnit();
    BoundingBox bbox = new BoundingBox();

    unit.name = reader.get("name");
    unit.adminLevel = reader.getInt("admin_level", 0);
    unit.population = reader.getDouble("population", -1);
    unit.area = reader.getDouble("area", 0);
    unit.density = reader.getDouble("density", -1);

    bbox.xMin = reader.getDouble("x1", 0);
    bbox.xMax = reader.getDouble("x3", 0);
    bbox.yMin = reader.getDouble("y1", 0);
    bbox.yMax = reader.getDouble("y2", 0);

    unit.bbox = bbox;
    return unit;
  }

  private void fixMissingValues() {
    units.forEach(AdminUnit::fixMissingValues);
  }

  private void setUnitRelationValues() {
    units.forEach(
        unit -> {
          long parentId = adminUnitToParentId.get(unit);
          unit.parent = idToAdminUnit.getOrDefault(parentId, null);

          if (unit.parent != null) {
            unit.parent.children = parentIdToChildren.getOrDefault(parentId, new ArrayList<>());
          }
        });
  }
}
