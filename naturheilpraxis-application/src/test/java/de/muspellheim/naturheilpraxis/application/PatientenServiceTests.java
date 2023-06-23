/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muspellheim.naturheilpraxis.domain.patienten.MemoryPatienten;
import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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

    Assertions.assertEquals(new Patientenkartei(List.of(newPatient())), kartei);
  }

  private static Patient newPatient() {
    return Patient.builder().name("Mustermann").vorname("Max").geboren(LocalDate.EPOCH).build();
  }
}
