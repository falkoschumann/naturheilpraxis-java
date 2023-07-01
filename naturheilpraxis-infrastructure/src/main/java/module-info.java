@SuppressWarnings("requires-automatic")
module de.muspellheim.naturheilpraxis.infrastructure {
  exports de.muspellheim.naturheilpraxis.infrastructure;

  requires transitive de.muspellheim.naturheilpraxis.domain;
  requires com.h2database;
  requires transitive java.sql;
  requires java.naming;
}
