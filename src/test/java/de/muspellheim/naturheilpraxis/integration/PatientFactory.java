/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.integration;

import de.muspellheim.naturheilpraxis.domain.Patient;
import java.time.LocalDate;

public class PatientFactory {
  private PatientFactory() {}

  public static Patient newPatient() {
    return newPatient(0);
  }

  public static Patient newPatient(long id) {
    return Patient.builder()
        .id(id)
        .name("Mustermann")
        .vorname("Max")
        .geboren(LocalDate.EPOCH)
        .build();
  }
}
