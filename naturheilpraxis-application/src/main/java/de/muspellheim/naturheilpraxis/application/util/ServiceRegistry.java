/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application.util;

import de.muspellheim.naturheilpraxis.domain.MemoryPatienten;
import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.domain.PatientenService;
import de.muspellheim.naturheilpraxis.domain.PatientenServiceImpl;
import java.time.LocalDate;

public class ServiceRegistry {
  private static final ServiceRegistry INSTANCE = new ServiceRegistry();

  private PatientenService patientenService;

  private ServiceRegistry() {
    var patienten = new MemoryPatienten();
    patienten.erzeuge(
        Patient.builder()
            .name("Daudrich")
            .vorname("Frederike")
            .geboren(LocalDate.now().minusDays(365 * 53 - 532))
            .annahmejahr(LocalDate.now().getYear() - 5)
            .strasse("Lohrstr. 2")
            .postleitzahl("59328")
            .wohnort("Süd Eren")
            .build());
    patienten.erzeuge(
        Patient.builder()
            .name("Kruschinski")
            .vorname("Mathilda")
            .geboren(LocalDate.now().minusDays(365 * 32 - 321))
            .annahmejahr(LocalDate.now().getYear() - 3)
            .strasse("Bertolt-Brecht-Str. 28")
            .postleitzahl("11997")
            .wohnort("Ludwigscheid")
            .build());
    patienten.erzeuge(
        Patient.builder()
            .name("Hügel")
            .vorname("Christiano")
            .geboren(LocalDate.now().minusDays(365 * 21 - 211))
            .annahmejahr(LocalDate.now().getYear() - 2)
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