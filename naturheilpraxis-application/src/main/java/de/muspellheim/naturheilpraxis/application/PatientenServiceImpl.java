/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import de.muspellheim.naturheilpraxis.domain.patienten.Patienten;

public class PatientenServiceImpl implements PatientenService {
  private final Patienten patienten;

  public PatientenServiceImpl(Patienten patienten) {
    this.patienten = patienten;
  }

  @Override
  public void nimmPatientAuf(Patient patient) {
    patienten.erzeuge(patient);
  }

  @Override
  public Patientenkartei lesePatientenkartei(String suchtext) {
    return new Patientenkartei(patienten.suche(suchtext));
  }
}
