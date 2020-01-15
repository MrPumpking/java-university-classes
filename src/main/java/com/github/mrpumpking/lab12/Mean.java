package com.github.mrpumpking.lab12;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mean {
  static double[] array;
  static BlockingQueue<Double> results;

  static void initArray(int size) {
    array = new Random().doubles(size, 1, 100).toArray();
  }

  static void parallelMean(int cnt) throws InterruptedException {
    MeanCalc threads[] = new MeanCalc[cnt];

    int threadArraySize = array.length / cnt;

    for (int i = 0; i < cnt; i++) {
      threads[i] = new MeanCalc(i * threadArraySize, (i + 1) * threadArraySize);
    }

    double t1 = System.nanoTime() / 1e6;
    Arrays.stream(threads).forEach(Thread::start);

    double t2 = System.nanoTime() / 1e6;
    for (MeanCalc thread : threads) {
      thread.join();
    }

    double t3 = System.nanoTime() / 1e6;
    System.out.printf(
        Locale.US,
        "Parallel v1: size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
        array.length,
        cnt,
        t2 - t1,
        t3 - t1,
        Arrays.stream(threads).mapToDouble(thread -> thread.mean).sum() / cnt);
  }

  static void parallelMeanV2(int cnt) throws InterruptedException {
    double sum = 0;
    MeanCalc threads[] = new MeanCalc[cnt];

    int threadArraySize = array.length / cnt;
    results = new ArrayBlockingQueue<>(cnt);

    for (int i = 0; i < cnt; i++) {
      threads[i] = new MeanCalc(i * threadArraySize, (i + 1) * threadArraySize);
    }

    double t1 = System.nanoTime() / 1e6;
    Arrays.stream(threads).forEach(Thread::start);

    double t2 = System.nanoTime() / 1e6;
    for (int i = 0; i < cnt; i++) {
      sum += results.take();
    }

    double t3 = System.nanoTime() / 1e6;
    System.out.printf(
        Locale.US,
        "Parallel v2: size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
        array.length,
        cnt,
        t2 - t1,
        t3 - t1,
        sum / cnt);
  }

  static class MeanCalc extends Thread {
    private final int start;
    private final int end;
    double mean;

    MeanCalc(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public void run() {
      for (int i = start; i < end; i++) {
        mean += array[i];
      }
      mean /= end - start;

      try {
        if (results != null) results.put(mean);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      System.out.printf(Locale.US, "%d-%d mean=%f\n", start, end, mean);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    initArray(1000);
    parallelMean(10);
    //    parallelMean(100);
    parallelMeanV2(10);
  }
}
