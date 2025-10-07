package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

/**
 * Enumeration representing the various statuses of an enrollment.
 */
public enum EnrollmentStatuses {
  REGULAR(1),
  TEMPORAL_RETIREMENT(2),
  RISK(3),
  LETTER_OF_PERMANENCE(4);

  private final int value;

  EnrollmentStatuses(int value) {
    this.value = value;
  }
}
