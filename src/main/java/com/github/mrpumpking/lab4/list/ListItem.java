package com.github.mrpumpking.lab4.list;

import com.github.mrpumpking.lab4.HTMLElement;

import java.io.PrintStream;

public class ListItem implements HTMLElement {
  private String content;

  public ListItem(String content) {
    this.content = content;
  }

  @Override
  public void writeHTML(PrintStream out) {
    out.printf("<li>%s</li>", content);
  }
}
