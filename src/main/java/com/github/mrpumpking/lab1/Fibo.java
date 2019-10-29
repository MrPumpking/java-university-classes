package com.github.mrpumpking.lab1;

import java.util.Scanner;

public class Fibo {

  public static void main(String[] args) {
    new Fibo();
  }

  public Fibo() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("How many fibonacci numbers would you like to generate: ");
    int element = scanner.nextInt();

    if (element < 1 || element > 45) {
      throw new IllegalArgumentException("Element must be within the range of 1 - 45");
    }

    int[] tab = new int[Math.max(element, 2)];

    tab[0] = 0;
    tab[1] = 1;

    for (int i = 2; i < element; i++) {
      tab[i] = tab[i - 2] + tab[i - 1];
    }

    for (int i = 0; i < element; i++) {
      System.out.println(tab[i]);
    }
  }
}
