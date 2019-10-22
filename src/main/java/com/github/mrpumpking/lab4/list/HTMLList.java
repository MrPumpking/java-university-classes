package com.github.mrpumpking.lab4.list;

import com.github.mrpumpking.lab4.HTMLElement;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public abstract class HTMLList implements HTMLElement {
  protected List<ListItem> items;

  public HTMLList() {
    this.items = new ArrayList<>();
  }

  public HTMLList addItem(ListItem item) {
    items.add(item);
    return this;
  }

  public HTMLList addItem(String content) {
    items.add(new ListItem(content));
    return this;
  }

  protected abstract String getElementTagName();

  @Override
  public void writeHTML(PrintStream out) {
    out.printf("<%s>", getElementTagName());
    items.forEach(listItem -> listItem.writeHTML(out));
    out.printf("</%s>", getElementTagName());
  }
}
