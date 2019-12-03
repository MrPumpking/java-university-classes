package com.github.mrpumpking.lab7;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminUnitQuery {
  AdminUnitList src;
  Predicate<AdminUnit> predicate = a -> true;
  Comparator<AdminUnit> comparator;
  int limit = Integer.MAX_VALUE;
  int offset = 0;

  AdminUnitQuery selectFrom(AdminUnitList src) {
    this.src = src;
    return this;
  }

  AdminUnitQuery where(Predicate<AdminUnit> predicate) {
    this.predicate = predicate;
    return this;
  }

  AdminUnitQuery and(Predicate<AdminUnit> predicate) {
    this.predicate = this.predicate.and(predicate);
    return this;
  }

  AdminUnitQuery or(Predicate<AdminUnit> predicate) {
    this.predicate = this.predicate.or(predicate);
    return this;
  }

  AdminUnitQuery sort(Comparator<AdminUnit> comparator) {
    this.comparator = comparator;
    return this;
  }

  AdminUnitQuery limit(int limit) {
    this.limit = limit;
    return this;
  }

  AdminUnitQuery offset(int offset) {
    this.offset = offset;
    return this;
  }

  AdminUnitList execute() {
    List<AdminUnit> result =
        src.units.stream()
            .skip(offset)
            .limit(limit)
            .filter(predicate)
            .sorted(comparator)
            .collect(Collectors.toList());

    return src.buildSubUnitList(result);
  }
}
