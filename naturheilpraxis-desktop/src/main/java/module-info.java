module de.muspellheim.naturheilpraxis.desktop {
  requires jdk.localedata;
  requires javafx.controls;
  requires javafx.fxml;
  requires de.muspellheim.naturheilpraxis.application;

  opens de.muspellheim.naturheilpraxis.desktop to
      javafx.graphics,
      javafx.fxml;
  opens de.muspellheim.naturheilpraxis.desktop.patienten to
      javafx.fxml,
      javafx.graphics;
  opens de.muspellheim.naturheilpraxis.desktop.util to
      javafx.fxml,
      javafx.graphics;
}
