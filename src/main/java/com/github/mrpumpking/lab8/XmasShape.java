package com.github.mrpumpking.lab8;

import java.awt.*;
import java.awt.geom.AffineTransform;

public interface XmasShape {

  /**
   * Przesuwa poczatek układu w zadane miejsce, skaluje, jeśli trzeba obraca
   *
   * @param g2d Graphics2D - kontekst graficzny
   */
  void transform(Graphics2D g2d);

  /**
   * Zawiera kod, który rysuje elementy
   *
   * @param g2d Graphics2D - kontekst graficzny
   */
  void render(Graphics2D g2d);

  /**
   * Standardowa implementacja metody
   *
   * @param g2d
   */
  default void draw(Graphics2D g2d) {
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // Get the current transform
    AffineTransform saveAT = g2d.getTransform();
    // Perform transformation
    transform(g2d);
    // Render
    render(g2d);
    // Restore original transform
    g2d.setTransform(saveAT);
  }

  public static void setGradientFill(Graphics2D g2d, int height, Color color1, Color color2) {
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
    g2d.setPaint(gp);
  }
}
