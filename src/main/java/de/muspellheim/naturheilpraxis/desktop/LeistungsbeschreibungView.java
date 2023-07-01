/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.desktop;

import de.muspellheim.naturheilpraxis.application.LeistungenService;
import de.muspellheim.naturheilpraxis.desktop.util.EventEmitter;
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

  @FXML private Stage stage;
  @FXML private DatePicker datumPicker;
  @FXML private TextField gebuehNrText;
  @FXML private TextField bezeichnungText;
  @FXML private TextField einzelpreisText;
  @FXML private Spinner<Integer> anzahlSpinner;
  @FXML private TextField gesamtpreisText;
  @FXML private Button speichernButton;

  private LeistungenService leistungenService;

  @FXML
  private void initialize() {
    datumPicker.setValue(LocalDate.now());
    anzahlSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999));

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
    var gesamtpreisBinding =
        Bindings.createStringBinding(
            () -> {
              try {
                var einzelpreis = Double.parseDouble(einzelpreisText.getText());
                var anzahl = anzahlSpinner.getValue();
                var gesamtpreis = new BigDecimal(einzelpreis * anzahl);
                return String.valueOf(gesamtpreis);
              } catch (NumberFormatException e) {
                e.printStackTrace();
                return "";
              }
            },
            einzelpreisText.textProperty(),
            anzahlSpinner.valueProperty());
    gesamtpreisText.textProperty().bind(gesamtpreisBinding);
  }

  void initLeistungenService(LeistungenService leistungenService) {
    this.leistungenService = leistungenService;
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
    stage.hide();
  }
}
