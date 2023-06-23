/*
 * Naturheilpraxis - Desktop
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.desktop.util;

import de.muspellheim.naturheilpraxis.application.PatientenService;
import de.muspellheim.naturheilpraxis.application.PatientenServiceImpl;
import de.muspellheim.naturheilpraxis.domain.patienten.MemoryPatienten;
import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import java.time.LocalDate;

public class ServiceRegistry {
  private static final ServiceRegistry INSTANCE = new ServiceRegistry();

  private final PatientenService patientenService;

  private ServiceRegistry() {
    var patienten = new MemoryPatienten();
    patienten.erzeuge(
        Patient.builder()
            .name("Daudrich")
            .vorname("Frederike")
            .geboren(LocalDate.now().minusDays(365 * 53 - 532))
            .strasse("Lohrstr. 2")
            .postleitzahl("59328")
            .wohnort("Süd Eren")
            .build());
    patienten.erzeuge(
        Patient.builder()
            .name("Kruschinski")
            .vorname("Mathilda")
            .geboren(LocalDate.now().minusDays(365 * 32 - 321))
            .strasse("Bertolt-Brecht-Str. 28")
            .postleitzahl("11997")
            .wohnort("Ludwigscheid")
            .build());
    patienten.erzeuge(
        Patient.builder()
            .name("Hügel")
            .vorname("Christiano")
            .geboren(LocalDate.now().minusDays(365 * 21 - 211))
            .strasse("Bornheimer Str. 28")
            .postleitzahl("45822")
            .wohnort("Thoreland")
            .build());
    patientenService = new PatientenServiceImpl(patienten);
  }

  public static PatientenService getPatientenService() {
    return INSTANCE.patientenService;
  }
}
