/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.util.List;

public interface LeistungRepository {
  void erzeuge(Leistung leistung);

  List<Leistung> suche(long patientId);
}
