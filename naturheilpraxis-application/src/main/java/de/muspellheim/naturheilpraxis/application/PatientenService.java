/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.domain.patienten.Patient;

public interface PatientenService {
  void nimmPatientAuf(Patient patient);

  Patientenkartei lesePatientenkartei(String suchtext);
}
