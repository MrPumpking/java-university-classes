package com.github.mrpumpking.lab4.list;

import com.github.mrpumpking.lab4.HTMLBaseTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListItemTest extends HTMLBaseTest {

  @Test
  void givenItemContent_whenWriteHtml_thenGenerateValidListItemMarkup() {
    String content = "Test";
    ListItem item = new ListItem(content);

    item.writeHTML(printStream);
    String result = getWrittenHTML();

    assertThat(result).contains("<li>").contains(content).contains("</li>");
  }
}
