package com.github.mrpumpking.lab1;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Podaj String: ");
    String s = scan.next();

    System.out.println("Podaj int:");
    int i = scan.nextInt();

    System.out.println("Podaj double:");
    double d = scan.nextDouble();

    System.out.printf("Wczytano %s , %d, %f", s, i, d);
  }
}
