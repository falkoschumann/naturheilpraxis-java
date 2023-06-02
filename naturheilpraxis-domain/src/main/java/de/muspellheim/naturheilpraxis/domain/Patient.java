/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;
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
    LocalDate geboren,
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
    String memo) {
  public boolean istPassend(String suchtext) {
    var wert = suchtext.toLowerCase();
    var liste =
        List.of(
            String.valueOf(nummer),
            Objects.requireNonNullElse(name, "").toLowerCase().toLowerCase(),
            Objects.requireNonNullElse(vorname, "").toLowerCase(),
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(geboren),
            Objects.requireNonNullElse(strasse, "").toLowerCase(),
            Objects.requireNonNullElse(postleitzahl, "").toLowerCase(),
            Objects.requireNonNullElse(wohnort, "").toLowerCase(),
            Objects.requireNonNullElse(telefon, "").toLowerCase(),
            Objects.requireNonNullElse(mobiltelefon, "").toLowerCase(),
            String.valueOf(annahmejahr));
    return liste.stream().anyMatch(t -> t.contains(wert));
  }
}
