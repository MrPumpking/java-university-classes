package com.github.mrpumpking.lab4;

import com.github.mrpumpking.lab4.paragraph.Paragraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section implements HTMLElement {
  private String title;
  private List<Paragraph> paragraphs;

  public Section() {
    this.paragraphs = new ArrayList<>();
  }

  public Section(String title) {
    this();
    this.title = title;
  }

  public Section setTitle(String title) {
    this.title = title;
    return this;
  }

  public Section addParagraph(String content) {
    paragraphs.add(new Paragraph(content));
    return this;
  }

  public Section addParagraph(Paragraph paragraph) {
    paragraphs.add(paragraph);
    return this;
  }

  @Override
  public void writeHTML(PrintStream out) {
    out.print("<section>");
    out.printf("<h2>%s</h2>", title);
    paragraphs.forEach(paragraph -> paragraph.writeHTML(out));
    out.print("</section>");
  }
}
