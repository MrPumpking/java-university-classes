package com.github.mrpumpking.kolokwium;

public class Book {
  // Ibuk ID;Tytuł;Autor;ISBN;Wydawnictwo;Rok wydania;Kategoria;Podkategoria;Link do książki
  public String id;
  public String title;
  public String author;
  public String isbn;
  public String publisher;
  public int year;
  public String category;
  public String subcategory;
  public String link;

  public int getYear() {
    return year;
  }

  @Override
  public String toString() {
    return "Book{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", author='"
        + author
        + '\''
        + ", isbn='"
        + isbn
        + '\''
        + ", publisher='"
        + publisher
        + '\''
        + ", year="
        + year
        + ", category='"
        + category
        + '\''
        + ", subcategory='"
        + subcategory
        + '\''
        + ", link='"
        + link
        + '\''
        + '}';
  }
}
