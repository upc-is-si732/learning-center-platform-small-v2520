package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Represents the unique identifier for a Professor.
 *
 * @param professorId the unique identifier for the professor
 */
@Embeddable
public record ProfessorId(Long professorId) {

  /**
   * Constructs a ProfessorId object with the specified professorId.
   *
   * @param professorId the unique identifier for the professor
   * @throws IllegalArgumentException if professorId is negative
   */
  public ProfessorId {
    if (professorId < 0) {
      throw new IllegalArgumentException("Professor professorId cannot be negative");
    }
  }

  /**
   * Constructs a ProfessorId object with a default value of 0.
   */
  public ProfessorId() {
    this(0L);
  }
}
