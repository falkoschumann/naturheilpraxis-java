/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application;

import de.muspellheim.naturheilpraxis.domain.Patient;
import de.muspellheim.naturheilpraxis.domain.PatientRepository;
import java.util.List;

public class PatientenServiceImpl implements PatientenService {
  private final PatientRepository patientRepository;

  public PatientenServiceImpl(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  @Override
  public void nimmPatientAuf(Patient patient) {
    patientRepository.erzeuge(patient);
  }

  @Override
  public List<Patient> lesePatientenkartei(String suchtext) {
    return patientRepository.suche(suchtext);
  }
}
