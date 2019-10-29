package com.github.mrpumpking;

import com.github.mrpumpking.lab4.Document;
import com.github.mrpumpking.lab4.paragraph.ParagraphWithList;

public class Main {
  public static void main(String[] args) {
    System.out.println("These are not the droids you are looking for...");

    Document cv = new Document("Jan Kowalski - CV");
    cv.addPhoto(
        "https://i0.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?fit=256%2C256&quality=100&ssl=1");
    cv.addSection("Wykształcenie")
        .addParagraph("2000-2005 Przedszkole im. Królewny Snieżki w ...")
        .addParagraph("2006-2012 SP7 im Ronalda Regana w ...")
        .addParagraph("...");
    cv.addSection("Umiejętności")
        .addParagraph(
            new ParagraphWithList()
                .setContent("Umiejętności")
                .addListItem("C")
                .addListItem("C++")
                .addListItem("Java"));

    cv.writeHTML(System.out);
  }
}
