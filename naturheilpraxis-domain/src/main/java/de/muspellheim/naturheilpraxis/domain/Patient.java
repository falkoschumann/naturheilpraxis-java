/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@SuppressWarnings("missing-explicit-ctor")
@Builder(toBuilder = true)
public record Patient(
    int nummer,
    int annahmejahr,
    String praxis,
    String anrede,
    String titel,
    String name,
    String vorname,
    LocalDate geburtstag,
    String strasse,
    String postleitzahl,
    String wohnort,
    String staat,
    String staatsangehoerigkeit,
    String beruf,
    String familienstand,
    String diagnose,
    String telefon,
    String mobiltelefon,
    String email,
    String partnerVon,
    String kindVon,
    List<String> eigenschaften,
    String memo) {}
