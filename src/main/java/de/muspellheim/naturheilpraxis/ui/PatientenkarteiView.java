/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.ui;

import de.muspellheim.naturheilpraxis.application.PatientenService;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.ui.util.EventEmitter;
import java.time.LocalDate;
import java.util.function.Consumer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class PatientenkarteiView {
  private final EventEmitter<Void> nimmNeuenPatientAuf = new EventEmitter<>();
  private final EventEmitter<Patient> erbringeLeistung = new EventEmitter<>();

  @FXML private Stage stage;
  @FXML private TextField suchtext;
  @FXML private TableView<Patient> patientenliste;
  @FXML private TableColumn<Patient, String> nameSpalte;
  @FXML private TableColumn<Patient, String> vornameSpalte;
  @FXML private TableColumn<Patient, LocalDate> geborenSpalte;
  @FXML private TableColumn<Patient, String> strasseSpalte;
  @FXML private TableColumn<Patient, String> postleitzahlSpalte;
  @FXML private TableColumn<Patient, String> wohnortSpalte;
  @FXML private Button erbringeLeistungButton;

  private PatientenService patientenService;

  @FXML
  private void initialize() {
    nameSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().name()));
    vornameSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().vorname()));
    geborenSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().geboren()));
    geborenSpalte.setCellFactory(c -> newGeborenTableCell());
    strasseSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().strasse()));
    postleitzahlSpalte.setCellValueFactory(
        f -> new SimpleObjectProperty<>(f.getValue().postleitzahl()));
    wohnortSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().wohnort()));

    erbringeLeistungButton
        .disableProperty()
        .bind(patientenliste.getSelectionModel().selectedItemProperty().isNull());
  }

  private TableCell<Patient, LocalDate> newGeborenTableCell() {
    var cell = new TextFieldTableCell<Patient, LocalDate>();
    cell.setConverter(new LocalDateStringConverter());
    return cell;
  }

  void initPatientenService(PatientenService patientenService) {
    this.patientenService = patientenService;
  }

  void addNimmNeuenPatientAufListener(Consumer<Void> listener) {
    nimmNeuenPatientAuf.addListener(listener);
  }

  void removeNimmNeuenPatientAufListener(Consumer<Void> listener) {
    nimmNeuenPatientAuf.removeListener(listener);
  }

  void addErbringeLeistungListener(Consumer<Patient> listener) {
    erbringeLeistung.addListener(listener);
  }

  void removeErbringeLeistungListener(Consumer<Patient> listener) {
    erbringeLeistung.removeListener(listener);
  }

  void run() {
    load();
    stage.show();
    suchtext.requestFocus();
  }

  void load() {
    var patienten = patientenService.lesePatientenkartei(suchtext.getText());
    patientenliste.getItems().setAll(patienten);
  }

  @FXML
  private void suchen() {
    load();
  }

  @FXML
  private void nimmPatientAuf() {
    nimmNeuenPatientAuf.emit(null);
  }

  @FXML
  private void erbringeLeistung() {
    var patient = patientenliste.getSelectionModel().getSelectedItem();
    erbringeLeistung.emit(patient);
  }
}
