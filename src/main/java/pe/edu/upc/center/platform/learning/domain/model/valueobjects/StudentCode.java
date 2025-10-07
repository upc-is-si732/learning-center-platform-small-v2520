package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.UUID;

/**
 * Represents a unique code for a student.
 *
 * @param studentCode the student code, it must not be null or blank and must be a valid UUID
 *
 * @author Open Source Application Development Team
 */
@Embeddable
public record StudentCode(String studentCode) {

  /**
   * Constructs a StudentCode object with a random UUID value.
   */
  public StudentCode() {
    this(UUID.randomUUID().toString());
  }

  /**
   * Constructs a StudentCode object with validation.
   *
   * @param studentCode the student code, it must not be null or blank and must be a valid UUID
   */
  public StudentCode {
    if (studentCode == null || studentCode.isBlank()) {
      throw new IllegalArgumentException("Student code cannot be null or blank");
    }
    if (studentCode.length() != 36) {
      throw new IllegalArgumentException("Student code must be 36 characters long");
    }
    if (!studentCode.matches("[a-f0-9]{8}-([a-f0-9]{4}-){3}[a-f0-9]{12}")) {
      throw new IllegalArgumentException("Student code must be a valid UUID");
    }
  }
}
