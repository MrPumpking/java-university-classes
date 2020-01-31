package com.github.mrpumpking.lab12;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Max {
  static double[] array;
  static BlockingQueue<double[]> results;

  static void initArray(int size) {
    array = new Random().doubles(size, 1, 100).toArray();
  }

  static double[] findMax(double[] array, int start, int end, int count) {
    return Arrays.stream(array).skip(start).limit(end).sorted().skip(end - start - count).toArray();
  }

  static void findMaxElements(int maxCount, int threadCnt) throws InterruptedException {
    double[] finalResult = new double[threadCnt * maxCount];
    MaxCalc[] threads = new MaxCalc[threadCnt];

    int threadArraySize = array.length / threadCnt;
    results = new ArrayBlockingQueue<>(threadCnt);

    for (int i = 0; i < threadCnt; i++) {
      threads[i] = new MaxCalc(i * threadArraySize, (i + 1) * threadArraySize, maxCount);
    }

    double t1 = System.nanoTime() / 1e6;
    Arrays.stream(threads).forEach(Thread::start);

    double t2 = System.nanoTime() / 1e6;

    for (int i = 0; i < threadCnt; i++) {
      double[] max = results.take();

      for (int j = 0; j < maxCount; j++) {
        finalResult[i * maxCount + j] = max[j];
      }
    }

    double t3 = System.nanoTime() / 1e6;
    System.out.printf(
        Locale.US,
        "Parallel v2: size = %d cnt=%d >  t2-t1=%f t3-t1=%f",
        array.length,
        threadCnt,
        t2 - t1,
        t3 - t1);

    System.out.println();
    System.out.println(
        "Maksymalne elementy: "
            + Arrays.toString(findMax(finalResult, 0, finalResult.length, maxCount)));
  }

  static class MaxCalc extends Thread {
    private final int start;
    private final int end;
    private final int maxCount;

    MaxCalc(int start, int end, int maxCount) {
      this.start = start;
      this.end = end;
      this.maxCount = maxCount;
    }

    @Override
    public void run() {
      try {
        results.put(findMax(array, start, end, maxCount));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    initArray(1000);
    findMaxElements(3, 10);
  }
}
