/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatientenServiceTests {
  @Test
  void nimmPatientAuf_SichertNeuenPatienten() {
    var patienten = new MemoryPatienten();
    var service = new PatientenServiceImpl(patienten);

    service.nimmPatientAuf(newPatient());

    assertEquals(List.of(newPatient()), patienten);
  }

  @Test
  void lesePatientenkartei_FindetPatienten() {
    var patienten = new MemoryPatienten();
    patienten.erzeuge(newPatient());
    var service = new PatientenServiceImpl(patienten);

    var kartei = service.lesePatientenkartei("muster");

    assertEquals(new Patientenkartei(List.of(newPatient())), kartei);
  }

  private static Patient newPatient() {
    return Patient.builder()
        .nummer(1)
        .name("Mustermann")
        .vorname("Max")
        .geboren(LocalDate.EPOCH)
        .annahmejahr(2023)
        .build();
  }
}
