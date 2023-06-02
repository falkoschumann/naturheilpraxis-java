/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class MemoryPatienten extends ArrayList<Patient> implements Patienten {
  @Serial private static final long serialVersionUID = 1;

  private int naechsteNummer = 1;

  public MemoryPatienten() {}

  @Override
  public void erzeuge(Patient patient) {
    patient = patient.toBuilder().nummer(naechsteNummer()).build();
    add(patient);
  }

  private int naechsteNummer() {
    return naechsteNummer++;
  }

  @Override
  public List<Patient> suche(String suchtext) {
    return this.stream().filter(p -> p.istPassend(suchtext)).toList();
  }
}
