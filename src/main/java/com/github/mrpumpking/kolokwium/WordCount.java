package com.github.mrpumpking.kolokwium;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

public class WordCount {
  private String text;
  private Map<Integer, Set<String>> lengthToWords;

  public static void main(String[] args) {
    new WordCount();
  }

  public WordCount() {
    lengthToWords = new TreeMap<>();
    text = readFile(getClass().getResource("/w-pustyni.txt").getPath(), Charset.forName("cp1250"));

    processWords();
    printStatistics();
  }

  private void printStatistics() {
    int i = 0;

    int longestLength = 0;
    int shortestLength = 0;
    Set<String> shortest = null;
    Set<String> longest = null;

    int median = -1;

    int medianA = -1;
    int medianB = -1;

    int sum = 0;

    for (Map.Entry<Integer, Set<String>> entry : lengthToWords.entrySet()) {
      sum += entry.getKey();

      if (i == 0) {
        shortest = entry.getValue();
        shortestLength = entry.getKey();
      }

      if (lengthToWords.size() % 2 == 0) {
        int half = (lengthToWords.size() - 2) / 2;

        if (i == half) {
          medianA = entry.getKey();
        } else if (i == half + 1) {
          medianB = entry.getKey();
        }

      } else {
        if (i == (lengthToWords.size() - 1) / 2) {
          median = entry.getKey();
        }
      }

      if (i == lengthToWords.size() - 1) {
        longest = entry.getValue();
        longestLength = entry.getKey();
      }

      i++;
    }

    if (medianA != -1) {
      median = (medianA + medianB) / 2;
    }

    int avg = sum / lengthToWords.size();

    System.out.println("Długość najkrótszych słów: " + shortestLength);
    System.out.println(shortest);

    System.out.println("Długość najdłuższych słów: " + longestLength);
    System.out.println(longest);

    System.out.println("Mediana: " + median);
    System.out.println(findClosest(median));

    System.out.println("Srednia: " + avg);
    System.out.println(findClosest(avg));
  }

  private Set<String> findClosest(int key) {
    int closest = Integer.MAX_VALUE;

    for (Map.Entry<Integer, Set<String>> entry : lengthToWords.entrySet()) {
      if (Math.abs(entry.getKey() - key) < Math.abs(entry.getKey() - closest)) {
        closest = entry.getKey();
      }
    }

    return lengthToWords.get(closest);
  }

  private void processWords() {
    String[] wordArray = text.split("[\\s|\\r|\\,|\\.|\\-|\\!|\\—|\\?]+");

    Arrays.stream(wordArray)
        .forEach(
            word -> {
              int length = word.length();

              if (lengthToWords.containsKey(length)) {
                lengthToWords.get(length).add(word);
              } else {
                Set<String> wordSet = new HashSet<>();
                wordSet.add(word);
                lengthToWords.put(length, wordSet);
              }
            });
  }

  public static String readFile(String name, Charset charset) {
    StringBuilder s = new StringBuilder();
    try (BufferedReader file =
        new BufferedReader(new InputStreamReader(new FileInputStream(name), charset))) {
      for (; ; ) {
        int c = file.read();
        if (c < 0) break;
        s.append((char) c);
      }
      return s.toString();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return "";
  }
}
