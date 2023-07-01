/*
 * Naturheilpraxis - Domain
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

public record Preis(long cents) {
  public Preis(double preis) {
    this((long) (preis * 100));
  }
}
