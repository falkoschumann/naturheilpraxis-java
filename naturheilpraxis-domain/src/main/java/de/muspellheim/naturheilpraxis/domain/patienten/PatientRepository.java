/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain.patienten;

import java.util.List;

public interface PatientRepository {
  void erzeuge(Patient patient);

  List<Patient> suche(String suchtext);
}
