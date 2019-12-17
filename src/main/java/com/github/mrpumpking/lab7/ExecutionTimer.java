package com.github.mrpumpking.lab7;

public class ExecutionTimer {
  private long start;
  private long end;

  public void start() {
    start = System.nanoTime();
  }

  public void stop() {
    end = System.nanoTime();
  }

  public void reset() {
    start = end = 0;
  }

  public long getExecutionTime() {
    return end - start;
  }
}
