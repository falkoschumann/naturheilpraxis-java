/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application.patienten;

import de.muspellheim.EventEmitter;
import de.muspellheim.naturheilpraxis.application.util.ServiceRegistry;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.domain.PatientenService;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Consumer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientenkarteiView {
  private final PatientenService patientenService = ServiceRegistry.getPatientenService();
  private final EventEmitter<Void> nimmNeuenPatientAufEvent = new EventEmitter<>();

  @FXML private Stage stage;
  @FXML private TextField suchtext;
  @FXML private TableView<Patient> patientenliste;
  @FXML private TableColumn<Patient, Integer> nummerSpalte;
  @FXML private TableColumn<Patient, String> anredeSpalte;
  @FXML private TableColumn<Patient, String> nameSpalte;
  @FXML private TableColumn<Patient, String> vornameSpalte;
  @FXML private TableColumn<Patient, String> geborenSpalte;
  @FXML private TableColumn<Patient, String> strasseSpalte;
  @FXML private TableColumn<Patient, String> postleitzahlSpalte;
  @FXML private TableColumn<Patient, String> wohnortSpalte;
  @FXML private TableColumn<Patient, String> telefonSpalte;

  public static PatientenkarteiView newInstance(Stage stage) {
    var viewClass = PatientenkarteiView.class;
    var filename = "/%s.fxml".formatted(viewClass.getSimpleName());
    try {
      var url = viewClass.getResource(filename);
      var loader = new FXMLLoader(url);
      loader.setRoot(stage);
      loader.load();
      return loader.getController();
    } catch (Exception e) {
      throw new IllegalStateException("Could not load view from file: %s".formatted(filename), e);
    }
  }

  @FXML
  private void initialize() {
    nummerSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().nummer()));
    anredeSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().anrede()));
    nameSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().name()));
    vornameSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().vorname()));
    geborenSpalte.setCellValueFactory(
        f ->
            new SimpleObjectProperty<>(
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    .format(f.getValue().geboren())));
    strasseSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().strasse()));
    postleitzahlSpalte.setCellValueFactory(
        f -> new SimpleObjectProperty<>(f.getValue().postleitzahl()));
    wohnortSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().wohnort()));
    telefonSpalte.setCellValueFactory(f -> new SimpleObjectProperty<>(f.getValue().telefon()));
  }

  public void run() {
    load();
    stage.show();
  }

  public void load() {
    var kartei = patientenService.lesePatientenkartei(suchtext.getText());
    patientenliste.getItems().setAll(kartei.patienten());
  }

  public void addNimmNeuenPatientAufListener(Consumer<Void> listener) {
    nimmNeuenPatientAufEvent.addListener(listener);
  }

  public void removeNimmNeuenPatientAufListener(Consumer<Void> listener) {
    nimmNeuenPatientAufEvent.removeListener(listener);
  }

  @FXML
  private void suchen() {
    load();
  }

  @FXML
  private void nimmNeuenPatientAuf() {
    nimmNeuenPatientAufEvent.emit(null);
  }
}
