/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muspellheim.naturheilpraxis.domain.Preis;
import org.junit.jupiter.api.Test;

class PreisTests {
  @Test
  void erzeuge_MitDouble() {
    var result = new Preis(47.11);

    assertEquals(new Preis(4711), result);
  }

  // TODO toDouble
  // TODO toString
  // TODO compare
  // TODO addiere, subtrahiere, multipliziere, dividiere
}
