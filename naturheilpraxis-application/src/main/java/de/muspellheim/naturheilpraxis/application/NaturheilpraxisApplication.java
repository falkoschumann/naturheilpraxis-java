/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.application.patient.PatientView;
import javafx.application.Application;
import javafx.stage.Stage;

public class NaturheilpraxisApplication extends Application {
  @Override
  public void start(Stage primaryStage) {
    var view = PatientView.newInstance(primaryStage);
    view.run();
  }
}
