package com.github.mrpumpking.lab7;

public class BoundingBox {
  double xMin;
  double xMax;
  double yMin;
  double yMax;

  void addPoint(double x, double y) {
    if (isEmpty()) {
      xMin = xMax = x;
      yMin = yMax = y;
      return;

    } else if (contains(x, y)) {
      return;
    }

    xMin = Math.min(xMin, x);
    xMax = Math.max(xMax, x);
    yMin = Math.min(yMin, y);
    yMax = Math.max(yMax, y);
  }

  boolean contains(double x, double y) {
    return !isEmpty() && (xMin <= x && x <= xMax) && (yMin <= y && y <= yMax);
  }

  boolean contains(BoundingBox bb) {
    return bb.isEmpty() || contains(bb.xMin, bb.yMin) && contains(bb.xMax, bb.yMax);
  }

  boolean intersects(BoundingBox bb) {
    return (!isEmpty() && !bb.isEmpty())
        && (xMin <= bb.xMax)
        && (xMax >= bb.xMin)
        && (yMin <= bb.yMax)
        && (yMax >= bb.yMin);
  }

  BoundingBox add(BoundingBox bb) {
    if (isEmpty()) {
      return bb;
    } else if (contains(bb)) {
      return this;
    } else if (bb.contains(this)) {
      return bb;
    }

    BoundingBox container = new BoundingBox();
    container.xMin = Math.min(xMin, bb.xMin);
    container.xMax = Math.max(xMax, bb.xMax);
    container.yMin = Math.min(yMin, bb.yMin);
    container.yMax = Math.max(yMax, bb.yMax);
    return container;
  }

  double getWidth() {
    return Math.abs(xMax - xMin);
  }

  double getHeight() {
    return Math.abs(yMax - yMin);
  }

  boolean isEmpty() {
    return Double.isNaN(xMin) && Double.isNaN(xMax) && Double.isNaN(yMin) && Double.isNaN(yMax);
  }

  double getCenterX() {
    if (isEmpty()) {
      throw new IllegalStateException("This bounding box is empty");
    }
    return (xMin + xMax) / 2;
  }

  double getCenterY() {
    if (isEmpty()) {
      throw new IllegalStateException("This bounding box is empty");
    }
    return (yMin + yMax) / 2;
  }

  double distanceTo(BoundingBox bb) {
    if (isEmpty() || bb.isEmpty()) {
      throw new IllegalStateException("Cannot calculate distance between empty BoundingBoxes");
    }
    double centerX = getCenterX();
    double centerY = getCenterY();
    double bbCenterX = bb.getCenterX();
    double bbCenterY = bb.getCenterY();

    return Haversine.distance(
        Math.min(centerY, bbCenterY),
        Math.min(centerX, bbCenterX),
        Math.max(centerY, bbCenterY),
        Math.max(centerX, bbCenterX));
  }

  @Override
  public String toString() {
    return "BoundingBox{"
        + "xMin="
        + xMin
        + ", xMax="
        + xMax
        + ", yMin="
        + yMin
        + ", yMax="
        + yMax
        + '}';
  }
}
