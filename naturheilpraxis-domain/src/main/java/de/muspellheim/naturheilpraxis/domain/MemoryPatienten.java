/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.io.Serial;
import java.util.ArrayList;

public class MemoryPatienten extends ArrayList<Patient> implements Patienten {
  @Serial private static final long serialVersionUID = 1;

  private int naechsteNummer = 1;

  public MemoryPatienten() {}

  @Override
  public void erzeuge(Patient patient) {
    patient = patient.toBuilder().nummer(naechsteNummer()).build();
    add(patient);
    System.out.println("Erzeuge " + patient);
  }

  private int naechsteNummer() {
    return naechsteNummer++;
  }
}
