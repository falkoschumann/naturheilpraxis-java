/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.domain.Leistung;
import de.muspellheim.naturheilpraxis.domain.LeistungRepository;

public class LeistungenServiceImpl implements LeistungenService {
  private final LeistungRepository leistungRepository;

  public LeistungenServiceImpl(LeistungRepository leistungRepository) {
    this.leistungRepository = leistungRepository;
  }

  @Override
  public void erbringeLeistung(Leistung leistung) {
    leistungRepository.erzeuge(leistung);
  }
}
