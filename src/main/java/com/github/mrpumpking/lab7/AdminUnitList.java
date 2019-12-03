package com.github.mrpumpking.lab7;

import com.github.mrpumpking.lab6.CSVReader;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminUnitList {
  private CSVReader reader;
  List<AdminUnit> units = new ArrayList<>();
  Map<Long, AdminUnit> idToAdminUnit = new HashMap<>();
  AdminUnit root = new AdminUnit();
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

  /**
   * @link http://www.mathcs.emory.edu/~cheung/Courses/554/Syllabus/3-index/R-tree.html
   * @link https://blog.mapbox.com/a-dive-into-spatial-search-algorithms-ebd0c5e39d2a
   */
  AdminUnitList getNeighbours(AdminUnit unit, double maxDistance) {
    AdminUnit current;
    List<AdminUnit> potentialNeighbours = new ArrayList<>();

    potentialNeighbours.add(root);

    while (true) {
      current = potentialNeighbours.remove(0);
      if (current.adminLevel < unit.adminLevel) {
        potentialNeighbours.addAll(current.children);
      }

      if (potentialNeighbours.isEmpty()) {
        break;
      }

      if (potentialNeighbours.get(0).adminLevel >= unit.adminLevel) {
        break;
      }
    }

    return buildSubUnitList(filterNeighbours(potentialNeighbours, unit, maxDistance));
  }

  AdminUnitList getNeighboursLinear(AdminUnit needle, double maxDistance) {
    return buildSubUnitList(filterNeighbours(units, needle, maxDistance));
  }

  AdminUnitList sortInplaceByName() {
    units.sort(new AdminUnitNameComparator());
    return this;
  }

  AdminUnitList sortInplaceByArea() {
    units.sort(
        new Comparator<AdminUnit>() {
          @Override
          public int compare(AdminUnit a, AdminUnit b) {
            return Double.compare(a.area, b.area);
          }
        });
    return this;
  }

  AdminUnitList sortInplaceByPopulation() {
    units.sort((a, b) -> Double.compare(a.population, b.population));
    return this;
  }

  AdminUnitList sortInplace(Comparator<AdminUnit> comparator) {
    units.sort(comparator);
    return this;
  }

  AdminUnitList sort(Comparator<AdminUnit> comparator) {
    AdminUnitList copy = copyOf();
    copy.sortInplace(comparator);
    return copy;
  }

  AdminUnitList filter(Predicate<AdminUnit> predicate) {
    List<AdminUnit> filtered = units.stream().filter(predicate).collect(Collectors.toList());
    return buildSubUnitList(filtered);
  }

  AdminUnitList filter(Predicate<AdminUnit> predicate, int offset, int limit) {
    List<AdminUnit> filtered =
        units.stream().skip(offset).limit(limit).filter(predicate).collect(Collectors.toList());
    return buildSubUnitList(filtered);
  }

  AdminUnitList copyOf() {
    return buildSubUnitList(units);
  }

  AdminUnitList buildSubUnitList(List<AdminUnit> list) {
    AdminUnitList unitList = new AdminUnitList();

    unitList.units = new ArrayList<>(list);
    unitList.idToAdminUnit =
        idToAdminUnit.entrySet().stream()
            .filter(x -> list.contains(x.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    unitList.adminUnitToParentId =
        adminUnitToParentId.entrySet().stream()
            .filter(x -> list.contains(x.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    unitList.adminUnitToParentId.put(root, -1L);
    unitList.idToAdminUnit.put(-1L, root);
    unitList.setUnitRelationValues();

    return unitList;
  }

  private List<AdminUnit> filterNeighbours(List<AdminUnit> list, AdminUnit needle, double maxDist) {
    return list.stream()
        .filter(
            unit ->
                !unit.equals(needle)
                    && ((unit.adminLevel >= 8 && unit.bbox.distanceTo(needle.bbox) < maxDist)
                        || (unit.adminLevel < 8 && unit.bbox.intersects(needle.bbox))))
        .collect(Collectors.toList());
  }

  private void extractAllAdminUnits() throws IOException, ColumnNotFoundException {
    idToAdminUnit.put(-1L, root);

    while (reader.next()) {
      AdminUnit unit = extractCurrentAdminUnit();

      units.add(unit);
      idToAdminUnit.put(reader.getLong("id"), unit);

      long parentId = reader.getLong("parent", -1);
      adminUnitToParentId.put(unit, parentId);

      if (parentIdToChildren.containsKey(parentId)) {
        parentIdToChildren.get(parentId).add(unit);
      } else {
        List<AdminUnit> children = new ArrayList<>();
        children.add(unit);
        parentIdToChildren.put(parentId, children);
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

    bbox.xMin = reader.getDouble("x1", Double.NaN);
    bbox.xMax = reader.getDouble("x3", Double.NaN);
    bbox.yMin = reader.getDouble("y1", Double.NaN);
    bbox.yMax = reader.getDouble("y3", Double.NaN);

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
