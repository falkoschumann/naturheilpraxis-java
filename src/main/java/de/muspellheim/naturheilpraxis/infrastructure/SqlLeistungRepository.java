/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.infrastructure;

import de.muspellheim.naturheilpraxis.domain.Leistung;
import de.muspellheim.naturheilpraxis.domain.LeistungRepository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SqlLeistungRepository implements LeistungRepository {
  private final Connection connection;

  public SqlLeistungRepository(Connection connection) {
    this.connection = connection;
  }

  public SqlLeistungRepository createSchema() {
    try (var statement = connection.createStatement()) {
      statement.execute(
          """
          CREATE TABLE IF NOT EXISTS leistungen (
            PRIMARY KEY (id),
            FOREIGN KEY (patient_id) REFERENCES patienten(id),
            id           BIGINT GENERATED ALWAYS AS IDENTITY,
            patient_id   BIGINT          NOT NULL,
            datum        DATE,
            gebueh_nr    VARCHAR(100),
            bezeichnung     VARCHAR(200),
            einzelpreis  NUMERIC(10, 2),
            anzahl       INTEGER
          );
          """);
      return this;
    } catch (SQLException e) {
      throw new UncheckedSqlException("Erzeuge Datenbankschema für Leistungen fehlgeschlagen.", e);
    }
  }

  @Override
  public void erzeuge(Leistung leistung) {
    var sql =
        """
        INSERT INTO leistungen (
          patient_id, datum, gebueh_nr, bezeichnung, einzelpreis, anzahl
        )
        VALUES (?, ?, ?, ?, ?, ?);
        """;
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, leistung.patientId());
      statement.setObject(2, leistung.datum());
      statement.setString(3, leistung.gebuehNr());
      statement.setString(4, leistung.bezeichnung());
      statement.setBigDecimal(5, leistung.einzelpreis());
      statement.setInt(6, leistung.anzahl());
      statement.execute();
    } catch (SQLException e) {
      throw new UncheckedSqlException("Erzeuge Leistung fehlgeschlagen.", e);
    }
  }

  @Override
  public List<Leistung> suche(long patientId) {
    var sql =
        """
        SELECT id, patient_id, datum, gebueh_nr, bezeichnung, einzelpreis, anzahl
          FROM leistungen
         WHERE patient_id = ?;
        """;
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, patientId);
      var resultSet = statement.executeQuery();
      return mapLeistungen(resultSet);
    } catch (SQLException e) {
      throw new UncheckedSqlException("Suche Leistungen fehlgeschlagen.", e);
    }
  }

  private static List<Leistung> mapLeistungen(ResultSet resultSet) throws SQLException {
    var leistungen = new ArrayList<Leistung>();
    while (resultSet.next()) {
      var leistung = mapLeistung(resultSet);
      leistungen.add(leistung);
    }
    return List.copyOf(leistungen);
  }

  private static Leistung mapLeistung(ResultSet resultSet) throws SQLException {
    return Leistung.builder()
        .id(resultSet.getLong("id"))
        .patientId(resultSet.getLong("patient_id"))
        .datum(resultSet.getObject("datum", LocalDate.class))
        .gebuehNr(resultSet.getString("gebueh_nr"))
        .bezeichnung(resultSet.getString("bezeichnung"))
        .einzelpreis(resultSet.getBigDecimal("einzelpreis"))
        .anzahl(resultSet.getInt("anzahl"))
        .build();
  }
}
