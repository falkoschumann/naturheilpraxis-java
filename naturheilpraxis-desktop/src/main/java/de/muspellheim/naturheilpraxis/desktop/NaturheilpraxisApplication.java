/*
 * Naturheilpraxis - Desktop
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.desktop;

import de.muspellheim.naturheilpraxis.desktop.patienten.PatientenkarteiView;
import de.muspellheim.naturheilpraxis.desktop.patienten.PatientenkarteikarteView;
import de.muspellheim.naturheilpraxis.desktop.util.FxmlControllerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class NaturheilpraxisApplication extends Application {
  private Stage primaryStage;
  private PatientenkarteiView patientenkarteiView;

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    patientenkarteiView =
        FxmlControllerFactory.newController(PatientenkarteiView.class, primaryStage);

    patientenkarteiView.addNimmNeuenPatientAufListener(e -> nimmNeuenPatientAuf());

    patientenkarteiView.run();
  }

  private void nimmNeuenPatientAuf() {
    var stage = new Stage();
    stage.initOwner(primaryStage);
    var patientView = FxmlControllerFactory.newController(PatientenkarteikarteView.class, stage);

    patientView.addNimmNeuenPatientAufListener(e -> patientenkarteiView.load());

    patientView.run();
  }
}
