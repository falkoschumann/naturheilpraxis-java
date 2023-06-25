module de.muspellheim.naturheilpraxis.application {
  exports de.muspellheim.naturheilpraxis.application.patienten;

  requires transitive de.muspellheim.naturheilpraxis.domain;
  requires de.muspellheim.naturheilpraxis.infrastructure;
}
