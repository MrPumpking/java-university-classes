package com.github.mrpumpking.kolokwium;

import com.github.mrpumpking.lab6.CSVReader;
import com.github.mrpumpking.lab6.exceptions.ColumnNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookList {
  private List<Book> books;
  private CSVReader reader;

  public BookList() {
    String filePath = getClass().getResource("/ibuk_wykaz_pozycji.csv").getPath();

    try {
      this.books = new ArrayList<>();
      this.reader = new CSVReader(filePath, ";", true);
      readBooks();

      //      ex1();
      ex2();

    } catch (IOException | ColumnNotFoundException e) {
      e.printStackTrace();
    }
  }

  //
  private void ex1() {
    books.stream().filter(book -> book.author.contains("Marek")).forEach(System.out::println);
  }

  private void ex2() {
    books.stream()
        .filter(
            book ->
                book.publisher.equals("Wydawnictwo Naukowe PWN")
                    && book.category.equals("Informatyka"))
        .sorted(Comparator.comparingInt(Book::getYear))
        .forEachOrdered(System.out::println);
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
