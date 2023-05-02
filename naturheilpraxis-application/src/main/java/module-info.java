module de.muspellheim.naturheilpraxis.application {
  requires jdk.localedata;
  requires javafx.controls;
  requires javafx.fxml;
  requires de.muspellheim.naturheilpraxis.domain;
  requires de.muspellheim.naturheilpraxis.infrastructure;

  opens de.muspellheim.naturheilpraxis.application to
      javafx.graphics,
      javafx.fxml;
  opens de.muspellheim.naturheilpraxis.application.patient to
      javafx.fxml,
      javafx.graphics;
}
