/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.desktop;

import de.muspellheim.naturheilpraxis.application.LeistungenService;
import de.muspellheim.naturheilpraxis.application.LeistungenServiceImpl;
import de.muspellheim.naturheilpraxis.application.PatientenService;
import de.muspellheim.naturheilpraxis.application.PatientenServiceImpl;
import de.muspellheim.naturheilpraxis.desktop.util.FxmlControllerFactory;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.infrastructure.DataSourceFactory;
import de.muspellheim.naturheilpraxis.infrastructure.SqlLeistungRepository;
import de.muspellheim.naturheilpraxis.infrastructure.SqlPatientRepository;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.stage.Stage;

public class NaturheilpraxisApplication extends Application {
  private PatientenService patientenService;
  private LeistungenService leistungenService;

  private Stage primaryStage;
  private PatientenkarteiView patientenkarteiView;

  @Override
  public void init() throws Exception {
    var dataSource = DataSourceFactory.newInMemory();
    var patientRepository = new SqlPatientRepository(dataSource.getConnection()).createSchema();
    patientenService = new PatientenServiceImpl(patientRepository);
    var leistungRepository = new SqlLeistungRepository(dataSource.getConnection()).createSchema();
    leistungenService = new LeistungenServiceImpl(leistungRepository);
    initDemo();
  }

  private void initDemo() {
    patientenService.nimmPatientAuf(
        Patient.builder()
            .name("Daudrich")
            .vorname("Frederike")
            .geboren(LocalDate.now().minusDays(365 * 53 - 532))
            .strasse("Lohrstr. 2")
            .postleitzahl("59328")
            .wohnort("Süd Eren")
            .build());
    patientenService.nimmPatientAuf(
        Patient.builder()
            .name("Kruschinski")
            .vorname("Mathilda")
            .geboren(LocalDate.now().minusDays(365 * 32 - 321))
            .strasse("Bertolt-Brecht-Str. 28")
            .postleitzahl("11997")
            .wohnort("Ludwigscheid")
            .build());
    patientenService.nimmPatientAuf(
        Patient.builder()
            .name("Hügel")
            .vorname("Christiano")
            .geboren(LocalDate.now().minusDays(365 * 21 - 211))
            .strasse("Bornheimer Str. 28")
            .postleitzahl("45822")
            .wohnort("Thoreland")
            .build());
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    patientenkarteiView =
        FxmlControllerFactory.newController(PatientenkarteiView.class, primaryStage);
    patientenkarteiView.initPatientenService(patientenService);

    patientenkarteiView.addNimmNeuenPatientAufListener(e -> nimmNeuenPatientAuf());
    patientenkarteiView.addErbringeLeistungListener(this::erbringeLeistung);

    patientenkarteiView.run();
  }

  private void nimmNeuenPatientAuf() {
    var stage = new Stage();
    stage.initOwner(primaryStage);
    var view = FxmlControllerFactory.newController(PatientenkarteikarteView.class, stage);
    view.initPatientenService(patientenService);

    view.addSpeichernListener(e -> patientenkarteiView.load());

    view.run();
  }

  private void erbringeLeistung(Patient patient) {
    var stage = new Stage();
    stage.initOwner(primaryStage);
    var view = FxmlControllerFactory.newController(LeistungsbeschreibungView.class, stage);
    view.initLeistungenService(leistungenService);

    view.addSpeichernListener(e -> System.out.println("Leistung gespeichert"));

    view.run();
  }
}
