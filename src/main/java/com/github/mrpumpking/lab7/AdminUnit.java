package com.github.mrpumpking.lab7;

public class AdminUnit {
  String name;
  int adminLevel;
  double population;
  double area;
  double density;
  AdminUnit parent;
  BoundingBox bbox = new BoundingBox();

  @Override
  public String toString() {
    return "AdminUnit{"
        + "name='"
        + name
        + '\''
        + ", adminLevel="
        + adminLevel
        + ", population="
        + population
        + ", area="
        + area
        + ", density="
        + density
        + ", parent="
        + parent
        + ", bbox="
        + bbox
        + '}';
  }
}
