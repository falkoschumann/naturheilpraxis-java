/*
 * Naturheilpraxis - Desktop
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.desktop.util;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class FxmlControllerFactory {
  private FxmlControllerFactory() {}

  public static <T> T newController(Class<T> controllerType, Stage stage) {
    var filename = "/%s.fxml".formatted(controllerType.getSimpleName());
    try {
      var url = controllerType.getResource(filename);
      var loader = new FXMLLoader(url);
      loader.setRoot(stage);
      loader.load();
      return loader.getController();
    } catch (Exception e) {
      throw new IllegalStateException("Could not load view from file: %s".formatted(filename), e);
    }
  }
}
