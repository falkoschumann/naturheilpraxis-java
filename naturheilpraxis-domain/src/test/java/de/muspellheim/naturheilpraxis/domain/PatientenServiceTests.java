/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.domain;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PatientenServiceTests {
  @Test
  void nimmPatientAuf_SichertNeuenPatienten() {
    var patienten = new MemoryPatienten();
    var service = new PatientenServiceImpl(patienten);

    service.nimmPatientAuf(newPatient(1));

    Assertions.assertEquals(List.of(newPatient(1)), patienten);
  }

  private static Patient newPatient(int nummer) {
    return Patient.builder().nummer(nummer).build();
  }
}
