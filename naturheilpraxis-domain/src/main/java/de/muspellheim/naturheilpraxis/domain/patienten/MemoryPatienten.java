/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain.patienten;

import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MemoryPatienten extends ArrayList<Patient> implements Patienten {
  @Serial private static final long serialVersionUID = 1;

  public MemoryPatienten() {}

  @Override
  public void erzeuge(Patient patient) {
    add(patient);
  }

  @Override
  public List<Patient> suche(String suchtext) {
    return this.stream().filter(p -> istGesucht(p, suchtext)).toList();
  }

  private static boolean istGesucht(Patient patient, String suchtext) {
    var wert = suchtext.toLowerCase();
    var liste =
        List.of(
            Objects.requireNonNullElse(patient.name(), "").toLowerCase(),
            Objects.requireNonNullElse(patient.vorname(), "").toLowerCase(),
            patient.geboren() != null
                ? DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(patient.geboren())
                : "",
            Objects.requireNonNullElse(patient.strasse(), "").toLowerCase(),
            Objects.requireNonNullElse(patient.postleitzahl(), "").toLowerCase(),
            Objects.requireNonNullElse(patient.wohnort(), "").toLowerCase());
    return liste.stream().anyMatch(t -> t.contains(wert));
  }
}
