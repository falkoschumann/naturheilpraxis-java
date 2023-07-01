/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muspellheim.naturheilpraxis.application.LeistungenService;
import de.muspellheim.naturheilpraxis.application.LeistungenServiceImpl;
import de.muspellheim.naturheilpraxis.domain.LeistungRepository;
import de.muspellheim.naturheilpraxis.domain.PatientRepository;
import de.muspellheim.naturheilpraxis.infrastructure.SqlLeistungRepository;
import de.muspellheim.naturheilpraxis.infrastructure.SqlPatientRepository;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeistungenServiceTests {
  private PatientRepository patientRepository;
  private LeistungRepository leistungRepository;
  private LeistungenService service;

  @BeforeEach
  void init() throws SQLException {
    var connection = DriverManager.getConnection("jdbc:h2:mem:");
    patientRepository = new SqlPatientRepository(connection).createSchema();
    leistungRepository = new SqlLeistungRepository(connection).createSchema();
    service = new LeistungenServiceImpl(leistungRepository);
  }

  @Test
  void erbringeLeistung_ErzeugtNeueLeistung() {
    var patient = PatientFactory.newPatient();
    patientRepository.erzeuge(patient);

    service.erbringeLeistung(LeistungFactory.newLeistung(1));

    assertEquals(List.of(LeistungFactory.newLeistung(1, 1)), leistungRepository.suche(1));
  }
}
