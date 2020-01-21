package com.github.mrpumpking.kolokwium;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

public class WordCount {
  private String text;
  private Map<Integer, Set<String>> words;

  public static void main(String[] args) {
    new WordCount();
  }

  public WordCount() {
    words = new TreeMap<>();
    text = readFile(getClass().getResource("/w-pustyni.txt").getPath(), Charset.forName("cp1250"));

    processWords();
    printShortestWords();
  }

  private void printShortestWords() {}

  private void processWords() {
    String[] wordArray = text.split("[\\s|\\r|\\,|\\.|\\-|\\!|\\—|\\?]+");

    Arrays.stream(wordArray)
        .forEach(
            word -> {
              int length = word.length();

              if (words.containsKey(length)) {
                words.get(length).add(word);
              } else {
                Set<String> wordSet = new HashSet<>();
                wordSet.add(word);
                words.put(length, wordSet);
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
