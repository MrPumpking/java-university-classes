package com.github.mrpumpking.lab4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document implements HTMLElement {
  private String title;
  private Photo photo;
  private List<Section> sections;

  public Document() {
    this.sections = new ArrayList<>();
  }

  public Document(String title) {
    this();
    this.title = title;
  }

  public Document setTitle(String title) {
    this.title = title;
    return this;
  }

  public Document addPhoto(String src) {
    this.photo = new Photo(src);
    return this;
  }

  public Section addSection(String title) {
    Section section = new Section(title);
    sections.add(section);
    return section;
  }

  public Document addSection(Section section) {
    sections.add(section);
    return this;
  }

  @Override
  public void writeHTML(PrintStream out) {
    out.print("<html>");
    out.printf("<head><title>%s</title></head>", title);
    out.print("<body>");
    photo.writeHTML(out);
    sections.forEach(section -> section.writeHTML(out));
    out.print("</body>");
    out.print("</html>");
  }
}
