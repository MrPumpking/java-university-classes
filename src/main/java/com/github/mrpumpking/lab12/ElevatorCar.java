package com.github.mrpumpking.lab12;

public class ElevatorCar extends Thread {
  int floor = 0;

  public int getFloor() {
    return floor;
  }

  enum Tour {
    UP,
    DOWN
  };

  Tour tour = Tour.UP;

  enum Movement {
    STOP,
    MOVING
  };

  Movement movementState = Movement.STOP;
}
