package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public record StudentCode(String studentCode) {
  public StudentCode() {
    this(UUID.randomUUID().toString());
  }
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
