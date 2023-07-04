/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.ui;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.util.converter.FormatStringConverter;

public class EuroStringConverter extends FormatStringConverter<BigDecimal> {

  public EuroStringConverter() {
    super(NumberFormat.getInstance(Locale.GERMANY));
    var format = (DecimalFormat) getFormat();
    format.setParseBigDecimal(true);
    format.setMinimumFractionDigits(2);
    format.setMaximumFractionDigits(2);
  }
}
