/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application.patienten;

import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import de.muspellheim.naturheilpraxis.domain.patienten.PatientRepository;
import de.muspellheim.naturheilpraxis.infrastructure.DataSourceFactory;
import de.muspellheim.naturheilpraxis.infrastructure.UncheckedSqlException;
import de.muspellheim.naturheilpraxis.infrastructure.patienten.SqlPatientRepository;
import java.sql.SQLException;

public class PatientenServiceImpl implements PatientenService {
  private final PatientRepository patientRepository;

  public PatientenServiceImpl() {
    try {
      var dataSource = DataSourceFactory.newInMemory();
      patientRepository = new SqlPatientRepository(dataSource.getConnection()).createSchema();
    } catch (SQLException e) {
      throw new UncheckedSqlException("Verbindung zur Datenbank kann nicht hergestellt werden.", e);
    }
  }

  public PatientenServiceImpl(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @Override
  public void nimmPatientAuf(Patient patient) {
    patientRepository.erzeuge(patient);
  }

  @Override
  public Patientenkartei lesePatientenkartei(String suchtext) {
    return new Patientenkartei(patientRepository.suche(suchtext));
  }
}
