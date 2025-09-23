package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * ClassroomId value object.
 * This class is used to represent the identifier of a Classroom.
 *
 * @param classroomId the classroomId
 */
@Embeddable
public record ClassroomId(String classroomId) {

  /**
   * Constructor for ClassroomId.
   *
   * @param classroomId the classroomId
   * @throws IllegalArgumentException if classroomId is null or blank
   */
  public ClassroomId {
    if (classroomId == null || classroomId.isBlank()) {
      throw new IllegalArgumentException("Classroom classroomId cannot be null or blank");
    }
  }

  /**
   * Default constructor for JPA.
   */
  public ClassroomId() {
    this(null);
  }
}
