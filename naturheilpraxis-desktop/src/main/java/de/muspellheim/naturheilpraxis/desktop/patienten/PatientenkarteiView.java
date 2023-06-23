/*
 * Naturheilpraxis - Desktop
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.desktop.patienten;

import de.muspellheim.naturheilpraxis.application.PatientenService;
import de.muspellheim.naturheilpraxis.desktop.util.EventEmitter;
import de.muspellheim.naturheilpraxis.desktop.util.ServiceRegistry;
import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import java.time.LocalDate;
import java.util.function.Consumer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientenkarteiView {
  private final PatientenService patientenService = ServiceRegistry.getPatientenService();
  private final EventEmitter<Void> nimmNeuenPatientAuf = new EventEmitter<>();

  @FXML private Stage stage;
  @FXML private TextField suchtext;
  @FXML private TableView<Patient> patientenliste;
  @FXML private TableColumn<Patient, String> nameSpalte;
  @FXML private TableColumn<Patient, String> vornameSpalte;
  @FXML private TableColumn<Patient, LocalDate> geborenSpalte;
  @FXML private TableColumn<Patient, String> strasseSpalte;
  @FXML private TableColumn<Patient, String> postleitzahlSpalte;
  @FXML private TableColumn<Patient, String> wohnortSpalte;

  @FXML
  private void initialize() {
    nameSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().name()));
    vornameSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().vorname()));
    geborenSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().geboren()));
    strasseSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().strasse()));
    postleitzahlSpalte.setCellValueFactory(
        f -> new SimpleObjectProperty<>(f.getValue().postleitzahl()));
    wohnortSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().wohnort()));
  }

  public void addNimmNeuenPatientAufListener(Consumer<Void> listener) {
    nimmNeuenPatientAuf.addListener(listener);
  }

  public void removeNimmNeuenPatientAufListener(Consumer<Void> listener) {
    nimmNeuenPatientAuf.removeListener(listener);
  }

  public void run() {
    load();
    stage.show();
  }

  public void load() {
    var kartei = patientenService.lesePatientenkartei(suchtext.getText());
    patientenliste.getItems().setAll(kartei.patienten());
  }

  @FXML
  private void suchen() {
    load();
  }

  @FXML
  private void nimmNeuenPatientAuf() {
    nimmNeuenPatientAuf.emit(null);
  }
}
