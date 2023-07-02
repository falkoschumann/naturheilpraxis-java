@SuppressWarnings("requires-automatic")
module de.muspellheim.naturheilpraxis {
  requires com.h2database;
  requires static lombok;
  requires java.naming;
  requires transitive java.sql;
  requires javafx.controls;
  requires javafx.fxml;
  requires jdk.localedata;

  opens de.muspellheim.naturheilpraxis.ui to
      javafx.fxml,
      javafx.graphics;
}
