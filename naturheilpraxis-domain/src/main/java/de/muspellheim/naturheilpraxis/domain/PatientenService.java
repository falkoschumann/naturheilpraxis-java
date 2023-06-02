/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

public interface PatientenService {
  void nimmPatientAuf(Patient patient);

  Patientenkartei lesePatientenkartei(String suchtext);
}
