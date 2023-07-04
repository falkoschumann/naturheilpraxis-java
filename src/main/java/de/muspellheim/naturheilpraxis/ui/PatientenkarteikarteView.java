/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.ui;

import de.muspellheim.naturheilpraxis.application.PatientenService;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.ui.util.EventEmitter;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientenkarteikarteView {
  private final EventEmitter<Void> speichern = new EventEmitter<>();

  @FXML private Stage stage;
  @FXML private TextField nameText;
  @FXML private TextField vornameText;
  @FXML private DatePicker geborenPicker;
  @FXML private TextField strasseText;
  @FXML private TextField postleitzahlText;
  @FXML private TextField wohnortText;
  @FXML private TextField berufText;
  @FXML private ChoiceBox<String> familienstandCombo;
  @FXML private Button speichernButton;

  private PatientenService patientenService;

  @FXML
  private void initialize() {
    familienstandCombo
        .getItems()
        .addAll("geschieden", "getrennt", "ledig", "verheiratet", "verwitwet");

    speichernButton
        .disableProperty()
        .bind(
            nameText
                .textProperty()
                .isEmpty()
                .and(vornameText.textProperty().isEmpty())
                .and(geborenPicker.valueProperty().isNull())
                .and(strasseText.textProperty().isEmpty())
                .and(postleitzahlText.textProperty().isEmpty())
                .and(wohnortText.textProperty().isEmpty())
                .and(berufText.textProperty().isEmpty())
                .and(familienstandCombo.valueProperty().isNull()));
  }

  void initPatientenService(PatientenService patientenService) {
    this.patientenService = patientenService;
  }

  void addSpeichernListener(Consumer<Void> listener) {
    speichern.addListener(listener);
  }

  void removeSpeichernListener(Consumer<Void> listener) {
    speichern.removeListener(listener);
  }

  void run() {
    stage.show();
    nameText.requestFocus();
  }

  @FXML
  private void abbrechen() {
    stage.hide();
  }

  @FXML
  private void speichern() {
    var patient =
        Patient.builder()
            .name(nameText.getText().trim())
            .vorname(vornameText.getText().trim())
            .geboren(geborenPicker.getValue())
            .strasse(strasseText.getText().trim())
            .postleitzahl(postleitzahlText.getText().trim())
            .wohnort(wohnortText.getText().trim())
            .beruf(berufText.getText().trim())
            .familienstand(familienstandCombo.getValue())
            .build();
    patientenService.nimmPatientAuf(patient);
    speichern.emit(null);
    stage.hide();
  }
}
