/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.application.patienten.PatientView;
import de.muspellheim.naturheilpraxis.application.patienten.PatientenkarteiView;
import javafx.application.Application;
import javafx.stage.Stage;

public class NaturheilpraxisApplication extends Application {
  @Override
  public void start(Stage primaryStage) {
    var patientenkarteiView = PatientenkarteiView.newInstance(primaryStage);

    patientenkarteiView.addNimmNeuenPatientAufListener(
        unused -> {
          var patientView = PatientView.newInstance(primaryStage);
          patientView.showAndWait();
          patientenkarteiView.load();
        });

    patientenkarteiView.run();
  }
}
