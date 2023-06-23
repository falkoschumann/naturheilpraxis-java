/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import de.muspellheim.naturheilpraxis.domain.patienten.Patienten;
import de.muspellheim.naturheilpraxis.infrastructure.DataSourceFactory;
import de.muspellheim.naturheilpraxis.infrastructure.UncheckedSqlException;
import de.muspellheim.naturheilpraxis.infrastructure.patienten.SqlPatienten;
import java.sql.SQLException;

public class PatientenServiceImpl implements PatientenService {
  private final Patienten patienten;

  public PatientenServiceImpl() {
    try {
      var dataSource = DataSourceFactory.newInMemory();
      patienten = new SqlPatienten(dataSource.getConnection()).createSchema();
    } catch (SQLException e) {
      throw new UncheckedSqlException("Verbindung zur Datenbank kann nicht hergestellt werden.", e);
    }
  }

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
