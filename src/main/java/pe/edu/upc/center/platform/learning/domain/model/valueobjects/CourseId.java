package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a Course ID.
 */
@Embeddable
public record CourseId(String courseId) {

  /**
   * Constructor with validation.
   */
  public CourseId {
    if (courseId == null || courseId.isBlank()) {
      throw new IllegalArgumentException("Course courseId cannot be null or blank");
    }
  }

  /**
   * JPA requires a default constructor: null.
   */
  public CourseId() {
    this(null);
  }
}
