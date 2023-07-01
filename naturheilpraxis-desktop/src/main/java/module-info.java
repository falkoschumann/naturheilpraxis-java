module de.muspellheim.naturheilpraxis.desktop {
  requires de.muspellheim.naturheilpraxis.application;
  requires de.muspellheim.naturheilpraxis.infrastructure;
  requires javafx.controls;
  requires javafx.fxml;
  requires jdk.localedata;

  opens de.muspellheim.naturheilpraxis.desktop to
      javafx.fxml,
      javafx.graphics;
}
