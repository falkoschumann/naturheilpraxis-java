/*
 * Naturheilpraxis - Infrastructure
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.infrastructure;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;

public class DataSourceFactory {
  private DataSourceFactory() {}

  public static DataSource newInMemory() {
    var ds = new JdbcDataSource();
    ds.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
    ds.setUser("sa");
    ds.setPassword("");
    return ds;
  }
}
