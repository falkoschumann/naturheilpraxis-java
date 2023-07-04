/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.ui;

import de.muspellheim.naturheilpraxis.application.LeistungenService;
import de.muspellheim.naturheilpraxis.domain.Leistung;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.ui.util.EventEmitter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LeistungsbeschreibungView {
  private final EventEmitter<Void> speichern = new EventEmitter<>();

  private final EuroStringConverter einzelpreisConverter = new EuroStringConverter();

  @FXML private Stage stage;
  @FXML private DatePicker datumPicker;
  @FXML private TextField gebuehNrText;
  @FXML private TextField bezeichnungText;
  @FXML private TextField einzelpreisText;
  @FXML private Spinner<Integer> anzahlSpinner;
  @FXML private TextField gesamtpreisText;
  @FXML private Button speichernButton;

  private LeistungenService leistungenService;
  private Patient patient;

  @FXML
  private void initialize() {
    datumPicker.setValue(LocalDate.now());
    anzahlSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1_000_000));

    speichernButton
        .disableProperty()
        .bind(
            datumPicker
                .valueProperty()
                .isNull()
                .and(gebuehNrText.textProperty().isEmpty())
                .and(bezeichnungText.textProperty().isEmpty())
                .and(einzelpreisText.textProperty().isEmpty())
                .and(anzahlSpinner.valueProperty().isNull()));
    gesamtpreisText
        .textProperty()
        .bind(
            Bindings.createStringBinding(
                this::berechneGesamtpreis,
                einzelpreisText.textProperty(),
                anzahlSpinner.valueProperty()));
  }

  private String berechneGesamtpreis() {
    var einzelpreis = einzelpreisConverter.fromString(einzelpreisText.getText());
    if (einzelpreis == null) {
      return "";
    }

    var anzahl = new BigDecimal(anzahlSpinner.getValue());
    var gesamtpreis = einzelpreis.multiply(anzahl);
    return einzelpreisConverter.toString(gesamtpreis);
  }

  void initLeistungenService(LeistungenService leistungenService) {
    this.leistungenService = leistungenService;
  }

  public void initPatient(Patient patient) {
    this.patient = patient;
  }

  void addSpeichernListener(Consumer<Void> listener) {
    speichern.addListener(listener);
  }

  void removeSpeichernListener(Consumer<Void> listener) {
    speichern.removeListener(listener);
  }

  void run() {
    stage.show();
    datumPicker.requestFocus();
  }

  @FXML
  private void abbrechen() {
    stage.hide();
  }

  @FXML
  private void speichern() {
    var leistung =
        Leistung.builder()
            .patientId(patient.id())
            .datum(datumPicker.getValue())
            .gebuehNr(gebuehNrText.getText())
            .bezeichnung(bezeichnungText.getText())
            .einzelpreis(einzelpreisConverter.fromString(einzelpreisText.getText()))
            .anzahl(anzahlSpinner.getValue())
            .build();
    leistungenService.erbringeLeistung(leistung);
    stage.hide();
  }
}
