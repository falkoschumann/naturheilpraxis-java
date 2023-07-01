/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.domain.Patient;
import java.util.List;

public interface PatientenService {
  void nimmPatientAuf(Patient patient);

  List<Patient> lesePatientenkartei(String suchtext);
}
