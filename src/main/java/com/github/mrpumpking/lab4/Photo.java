package com.github.mrpumpking.lab4;

import java.io.PrintStream;

public class Photo implements HTMLElement {
  private String src;

  public Photo(String src) {
    this.src = src;
  }

  @Override
  public void writeHTML(PrintStream out) {
    out.printf("<img src=\"%s\" alt=\"CV Photo\" height=\"42\" width=\"42\">", src);
  }
}
