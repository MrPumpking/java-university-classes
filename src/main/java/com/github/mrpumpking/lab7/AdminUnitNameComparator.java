package com.github.mrpumpking.lab7;

import java.util.Comparator;

public class AdminUnitNameComparator implements Comparator<AdminUnit> {

  @Override
  public int compare(AdminUnit a, AdminUnit b) {
    return a.name.compareTo(b.name);
  }

}
