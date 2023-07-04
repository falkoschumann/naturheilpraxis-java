/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.infrastructure;

import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.domain.PatientRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqlPatientRepository implements PatientRepository {
  private final Connection connection;

  public SqlPatientRepository(Connection connection) {
    this.connection = connection;
  }

  public SqlPatientRepository createSchema() {
    try (var statement = connection.createStatement()) {
      statement.execute(
          """
          CREATE TABLE IF NOT EXISTS patienten (
            PRIMARY KEY (id),
            id            BIGINT        NOT NULL GENERATED ALWAYS AS IDENTITY,
            name          VARCHAR(100),
            vorname       VARCHAR(100),
            geboren       DATE,
            strasse       VARCHAR(200),
            postleitzahl  VARCHAR(50),
            wohnort       VARCHAR(100),
            beruf         VARCHAR(100),
            familienstand VARCHAR(50)
          );
          """);
      return this;
    } catch (SQLException e) {
      throw new UncheckedSqlException("Erzeuge Datenbankschema für Patienten fehlgeschlagen.", e);
    }
  }

  @Override
  public void erzeuge(Patient patient) {
    var sql =
        """
        INSERT INTO patienten (
          name, vorname, geboren, strasse, postleitzahl, wohnort, beruf, familienstand
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;
    try (var statement = connection.prepareStatement(sql)) {
      statement.setString(1, patient.name());
      statement.setString(2, patient.vorname());
      statement.setObject(3, patient.geboren());
      statement.setString(4, patient.strasse());
      statement.setString(5, patient.postleitzahl());
      statement.setString(6, patient.wohnort());
      statement.setString(7, patient.beruf());
      statement.setString(8, patient.familienstand());
      statement.execute();
    } catch (SQLException e) {
      throw new UncheckedSqlException("Erzeuge Patient fehlgeschlagen: %s.".formatted(patient), e);
    }
  }

  @Override
  public List<Patient> suche(String suchtext) {
    var sql =
        """
        SELECT id, name, vorname, geboren, strasse, postleitzahl, wohnort, beruf, familienstand
          FROM patienten
         WHERE name ILIKE ?
            OR vorname ILIKE ?
            OR to_char(geboren, 'DD.MM.YYYY') ILIKE ?
            OR strasse ILIKE ?
            OR postleitzahl ILIKE ?
            OR wohnort ILIKE ?
            OR beruf ILIKE ?
            OR familienstand ILIKE ?;
        """;
    suchtext = "%" + suchtext + "%";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setString(1, suchtext);
      statement.setString(2, suchtext);
      statement.setObject(3, suchtext);
      statement.setString(4, suchtext);
      statement.setString(5, suchtext);
      statement.setString(6, suchtext);
      statement.setString(7, suchtext);
      statement.setString(8, suchtext);
      var resultSet = statement.executeQuery();
      return mapPatienten(resultSet);
    } catch (SQLException e) {
      throw new UncheckedSqlException("Suche Patient fehlgeschlagen: %s.".formatted(suchtext), e);
    }
  }

  private static List<Patient> mapPatienten(ResultSet resultSet) throws SQLException {
    var patienten = new ArrayList<Patient>();
    while (resultSet.next()) {
      var patient = mapPatient(resultSet);
      patienten.add(patient);
    }
    return List.copyOf(patienten);
  }

  private static Patient mapPatient(ResultSet resultSet) throws SQLException {
    return Patient.builder()
        .id(resultSet.getLong("id"))
        .name(resultSet.getString("name"))
        .vorname(resultSet.getString("vorname"))
        .geboren(resultSet.getObject("geboren", LocalDate.class))
        .strasse(resultSet.getString("strasse"))
        .postleitzahl(resultSet.getString("postleitzahl"))
        .wohnort(resultSet.getString("wohnort"))
        .beruf(resultSet.getString("beruf"))
        .familienstand(resultSet.getString("familienstand"))
        .build();
  }
}
