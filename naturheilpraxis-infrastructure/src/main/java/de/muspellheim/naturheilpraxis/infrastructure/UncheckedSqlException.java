/*
 * Naturheilpraxis - Infrastructure
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.infrastructure;

import java.io.Serial;
import java.sql.SQLException;

public class UncheckedSqlException extends RuntimeException {
  @Serial private static final long serialVersionUID = 0L;

  public UncheckedSqlException(String message, SQLException cause) {
    super(message, cause);
  }

  public UncheckedSqlException(SQLException cause) {
    super(cause);
  }

  @Override
  public synchronized SQLException getCause() {
    return (SQLException) super.getCause();
  }
}
