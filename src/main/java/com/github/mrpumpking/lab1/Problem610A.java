package com.github.mrpumpking.lab1;

import java.util.Scanner;

public class Problem610A {
  public static void main(String[] args) {
    new Problem610A();
  }

  public Problem610A() {
    Scanner in = new Scanner(System.in);
    int length = in.nextInt();

    if (length % 2 == 1) {
      System.out.println(0);
    } else if (length % 4 != 0) {
      System.out.println(length / 4);
    } else {
      System.out.println(length / 4 - 1);
    }
  }
}
