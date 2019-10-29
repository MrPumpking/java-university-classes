package com.github.mrpumpking.lab4.paragraph;

import com.github.mrpumpking.lab4.HTMLBaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParagraphTest extends HTMLBaseTest {

  @Test
  void givenContentString_whenWriteHTML_thenGenerateValidHTMLParagraphMarkup() {
    String content = "Test";
    Paragraph paragraph = new Paragraph(content);

    paragraph.writeHTML(printStream);
    String result = getWrittenHTML();
    assertThat(result).contains("<p>").contains(content).contains("</p>");
  }
}
