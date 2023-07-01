/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.integration;

import de.muspellheim.naturheilpraxis.domain.Leistung;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LeistungFactory {
  private LeistungFactory() {}

  public static Leistung newLeistung(long patientId) {
    return newLeistung(0, patientId);
  }

  public static Leistung newLeistung(int id, long patientId) {
    return Leistung.builder()
        .id(id)
        .patientId(patientId)
        .datum(LocalDate.now())
        .gebuehNr("08/15")
        .bezeichnung("Foobar")
        .einzelpreis(new BigDecimal("47.11"))
        .anzahl(2)
        .build();
  }
}
