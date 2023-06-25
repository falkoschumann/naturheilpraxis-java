/*
 * Naturheilpraxis - Application
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.application.patienten;

import de.muspellheim.naturheilpraxis.domain.patienten.Patient;
import java.util.List;

public record Patientenkartei(List<Patient> patienten) {}
