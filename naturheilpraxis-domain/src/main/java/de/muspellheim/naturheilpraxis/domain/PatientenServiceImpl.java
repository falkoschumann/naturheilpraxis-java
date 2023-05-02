/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

public class PatientenServiceImpl implements PatientenService {
  private final Patienten patienten;

  public PatientenServiceImpl(Patienten patienten) {
    this.patienten = patienten;
  }

  @Override
  public void nimmPatientAuf(Patient patient) {
    patienten.erzeuge(patient);
  }
}
