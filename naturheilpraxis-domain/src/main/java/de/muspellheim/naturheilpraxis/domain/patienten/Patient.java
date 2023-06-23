/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain.patienten;

import java.time.LocalDate;
import lombok.Builder;

@SuppressWarnings("missing-explicit-ctor")
@Builder(toBuilder = true)
public record Patient(
    long id,
    String name,
    String vorname,
    LocalDate geboren,
    String strasse,
    String postleitzahl,
    String wohnort,
    String beruf,
    String familienstand) {}
