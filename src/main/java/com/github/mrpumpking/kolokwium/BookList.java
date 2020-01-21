package com.github.mrpumpking.kolokwium;

import com.github.mrpumpking.lab6.CSVReader;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;
import java.util.*;

public class BookList {
  private List<Book> books;
  private CSVReader reader;

  public BookList() {
    String filePath = getClass().getResource("/ibuk_wykaz_pozycji.csv").getPath();

    try {
      this.books = new ArrayList<>();
      this.reader = new CSVReader(filePath, ";", true);
      readBooks();

      exA();
      exB();
      exC();
      exD();

    } catch (IOException | ColumnNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Wypisz wszystkie książki, których autorem lub współautorem był mężczyzna o imieniu Marek.
   * Wypisując informacje o książce podaj co najmniej: autora, tytuł, wydawnictwo i rok wydania
   */
  private void exA() {
    books.stream().filter(book -> book.author.contains("Marek")).forEach(System.out::println);
  }

  /**
   * Wypisz wszystkie książki wydane przez Wydawnictwo Naukowe PWN z kategorii Informatyka
   * posortowane według roku wydania
   */
  private void exB() {
    books.stream()
        .filter(
            book ->
                book.publisher.equals("Wydawnictwo Naukowe PWN")
                    && book.category.equals("Informatyka"))
        .sorted(Comparator.comparingInt(Book::getYear))
        .forEachOrdered(System.out::println);
  }

  /** Podaj ile książek wydano w kolejnych latach (policz książki z kolejnych lat i wypisz) */
  private void exC() {
    Map<Integer, Integer> yearBookCount = new HashMap<>();
    books.forEach(book -> yearBookCount.compute(book.year, (k, v) -> (v == null) ? 1 : v + 1));
    System.out.println(yearBookCount);
  }

  /** Podaj ile książek zostało wydane przez poszczególne wydawnictwa */
  private void exD() {
    Map<String, Integer> publisherBookCount = new HashMap<>();
    books.forEach(
        book -> publisherBookCount.compute(book.publisher, (k, v) -> (v == null) ? 1 : v + 1));
    System.out.println(publisherBookCount);
  }

  private void readBooks() throws IOException, ColumnNotFoundException {
    while (reader.next()) {
      Book book = new Book();
      book.id = reader.get("Ibuk ID", "");
      book.title = reader.get("Tytuł", "");
      book.author = reader.get("Autor", "");
      book.isbn = reader.get("ISBN");
      book.publisher = reader.get("Wydawnictwo");
      book.year = reader.getInt("Rok wydania", -1);
      book.category = reader.get("Kategoria");
      book.subcategory = reader.get("Podkategoria");
      book.link = reader.get("Link do książki");

      books.add(book);
    }
  }

  public static void main(String[] args) {
    new BookList();
  }
}
