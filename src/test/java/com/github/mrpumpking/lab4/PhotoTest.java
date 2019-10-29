package com.github.mrpumpking.lab4;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoTest extends HTMLBaseTest {

  @Test
  void givenPhotoUrl_whenWriteHtml_thenWriteValidHTMLImgMarkup() {
    String imageURL = "photo.png";
    Photo photo = new Photo(imageURL);

    photo.writeHTML(printStream);
    String result = getWrittenHTML();

    assertThat(result).contains("<img").contains("src=\"").contains(imageURL).contains("/>");
  }
}
