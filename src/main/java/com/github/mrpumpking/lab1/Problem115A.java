package com.github.mrpumpking.lab1;

import java.util.Scanner;

public class Problem115A {

  public static void main(String[] args) {
    new Problem115A();
  }

  public Problem115A() {
    Scanner scanner = new Scanner(System.in);
    int workerCount = scanner.nextInt();

    int answer = 0;
    int count = 0;

    int[] workers = new int[workerCount + 9];

    for (int i = 1; i <= workerCount; i++) {
      workers[i] = scanner.nextInt();
    }

    for (int i = 1; i <= workerCount; i++) {
      count = 0;
      int worker = workers[i];

      while (worker != -1) {
        worker = workers[worker];
        count++;
      }

      answer = Math.max(answer, count);
    }

    System.out.println(answer + 1);
  }
}
