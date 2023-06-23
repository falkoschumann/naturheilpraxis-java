/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import de.muspellheim.naturheilpraxis.infrastructure.patienten.SqlPatienten;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientenServiceTests {
  private SqlPatienten patienten;

  @BeforeEach
  void init() throws SQLException {
    var connection = DriverManager.getConnection("jdbc:h2:mem:");
    patienten = new SqlPatienten(connection);
    patienten.createSchema();
  }

  @Test
  void nimmPatientAuf_SichertNeuenPatienten() {
    var service = new PatientenServiceImpl(patienten);

    service.nimmPatientAuf(newPatient());

    assertEquals(List.of(newPatient(1)), patienten.suche(""));
  }

  @Test
  void lesePatientenkartei_FindetPatienten() {
    patienten.erzeuge(newPatient());
    var service = new PatientenServiceImpl(patienten);

    var kartei = service.lesePatientenkartei("muster");

    Assertions.assertEquals(new Patientenkartei(List.of(newPatient(1))), kartei);
  }

  private static Patient newPatient() {
    return newPatient(0);
  }

  private static Patient newPatient(long id) {
    return Patient.builder()
        .id(id)
        .name("Mustermann")
        .vorname("Max")
        .geboren(LocalDate.EPOCH)
        .build();
  }
}
