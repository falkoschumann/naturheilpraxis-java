package de.muspellheim.naturheilpraxis.infrastructure.leistungen;

import de.muspellheim.naturheilpraxis.domain.leistungen.Leistung;
import de.muspellheim.naturheilpraxis.domain.leistungen.LeistungRepository;
import de.muspellheim.naturheilpraxis.infrastructure.UncheckedSqlException;
import java.sql.Connection;
import java.sql.SQLException;

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
            FOREIGN KEY (patientId) REFERENCES patienten(id),
            id          BIGINT GENERATED ALWAYS AS IDENTITY,
            patientId   BIGINT          NOT NULL,
            datum       DATE,
            gebueh_nr   VARCHAR(100),
            leistung    VARCHAR(200),
            einzelpreis NUMERIC(10, 2),
            anzahl      INTEGER,
            gesamtpreis NUMERIC(10, 2)
          );
          """);
      return this;
    } catch (SQLException e) {
      throw new UncheckedSqlException("Erzeuge Datenbankschema für Patienten fehlgeschlagen.", e);
    }
  }

  @Override
  public void erzeuge(Leistung leistung) {
    var sql =
        """
      INSERT INTO leistungen (
        patientId, datum, gebueh_nr, leistung, einzelpreis, anzahl, gesamtpreis
      )
      VALUES (?, ?, ?, ?, ?, ?, ?, ?);
      """;
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, leistung.patientId());
      statement.setObject(2, leistung.datum());
      statement.setString(3, leistung.gebuehNr());
      statement.setString(4, leistung.leistung());
      statement.setBigDecimal(
          5, leistung.einzelpreis() != null ? leistung.einzelpreis().toBigDecimal() : null);
      statement.setInt(6, leistung.anzahl());
      statement.setBigDecimal(
          7, leistung.gesamtpreis() != null ? leistung.gesamtpreis().toBigDecimal() : null);
      statement.execute();
    } catch (SQLException e) {
      throw new UncheckedSqlException("Erzeuge Patient fehlgeschlagen.", e);
    }
  }
}
