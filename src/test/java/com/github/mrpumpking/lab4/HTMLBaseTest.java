package com.github.mrpumpking.lab4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class HTMLBaseTest {
  protected PrintStream printStream;
  private ByteArrayOutputStream outputStream;

  @BeforeEach
  void setUp() {
    this.outputStream = new ByteArrayOutputStream();
    this.printStream = new PrintStream(outputStream);
  }

  @AfterEach
  void cleanUp() throws IOException {
    this.printStream.close();
    this.outputStream.close();
  }

  protected String getWrittenHTML() {
    try {
      return outputStream.toString("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }
}
