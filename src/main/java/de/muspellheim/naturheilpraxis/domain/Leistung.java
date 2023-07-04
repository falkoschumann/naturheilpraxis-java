/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@SuppressWarnings("missing-explicit-ctor")
@Builder
public record Leistung(
    long id,
    long patientId,
    LocalDate datum,
    String gebuehNr,
    String bezeichnung,
    BigDecimal einzelpreis,
    Integer anzahl) {}
