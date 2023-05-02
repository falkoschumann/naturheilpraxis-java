/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application.patient;

import de.muspellheim.naturheilpraxis.domain.MemoryPatienten;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.domain.PatientenServiceImpl;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientView {
  @FXML private Stage stage;
  @FXML private Spinner<Integer> annahmejahr;
  @FXML private ChoiceBox<String> praxis;
  @FXML private ChoiceBox<String> anrede;
  @FXML private TextField titel;
  @FXML private TextField name;
  @FXML private TextField vorname;
  @FXML private DatePicker geburtstag;
  @FXML private TextField strasse;
  @FXML private TextField postleitzahl;
  @FXML private TextField wohnort;
  @FXML private TextField staat;
  @FXML private TextField staatsangehoerigkeit;
  @FXML private TextField beruf;
  @FXML private ChoiceBox<String> familienstand;
  @FXML private TextArea diagnose;

  public static PatientView newInstance(Stage stage) {
    var viewClass = PatientView.class;
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
    var annahmejahrValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 9999);
    annahmejahrValueFactory.setValue(LocalDate.now().getYear());
    annahmejahr.setValueFactory(annahmejahrValueFactory);

    praxis.getItems().addAll("Leipzig");
    anrede.getItems().addAll("Herr", "Frau", "Mr", "Ms");
    familienstand.getItems().addAll("geschieden", "getrennt", "ledig", "verheiratet", "verwitwet");
  }

  public void run() {
    stage.show();
  }

  @FXML
  private void fuegeEigenschaftHinzu() {}

  @FXML
  private void entferneEigenschaft() {}

  @FXML
  private void abbrechen() {}

  @FXML
  private void speichern() {
    var patienten = new MemoryPatienten();
    var patientenService = new PatientenServiceImpl(patienten);
    var patient =
        Patient.builder()
            .annahmejahr(annahmejahr.getValue())
            .praxis(praxis.getValue())
            .anrede(anrede.getValue())
            .titel(titel.getText())
            .name(name.getText())
            .vorname(vorname.getText())
            .geburtstag(geburtstag.getValue())
            .strasse(strasse.getText())
            .postleitzahl(postleitzahl.getText())
            .wohnort(wohnort.getText())
            .staat(staat.getText())
            .staatsangehoerigkeit(staatsangehoerigkeit.getText())
            .beruf(beruf.getText())
            .familienstand(familienstand.getValue())
            .diagnose(diagnose.getText())
            .build();
    patientenService.nimmPatientAuf(patient);
  }
}
