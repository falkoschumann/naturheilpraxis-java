/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muspellheim.naturheilpraxis.application.PatientenService;
import de.muspellheim.naturheilpraxis.application.PatientenServiceImpl;
import de.muspellheim.naturheilpraxis.domain.PatientRepository;
import de.muspellheim.naturheilpraxis.infrastructure.SqlPatientRepository;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientenServiceTests {
  private PatientRepository patientRepository;
  private PatientenService service;

  @BeforeEach
  void init() throws SQLException {
    var connection = DriverManager.getConnection("jdbc:h2:mem:");
    patientRepository = new SqlPatientRepository(connection).createSchema();
    service = new PatientenServiceImpl(patientRepository);
  }

  @Test
  void nimmPatientAuf_ErzeugtNeuenPatienten() {
    service.nimmPatientAuf(PatientFactory.newPatient());

    assertEquals(List.of(PatientFactory.newPatient(1)), patientRepository.suche(""));
  }

  @Test
  void lesePatientenkartei_FindetPatienten() {
    patientRepository.erzeuge(PatientFactory.newPatient());

    var kartei = service.lesePatientenkartei("muster");

    Assertions.assertEquals(List.of(PatientFactory.newPatient(1)), kartei);
  }
}
