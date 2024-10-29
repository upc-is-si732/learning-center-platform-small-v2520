package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CourseId(String courseId) {
    public CourseId {
      if (courseId == null || courseId.isBlank()) {
        throw new IllegalArgumentException("Course courseId cannot be null or blank");
      }
    }

    public CourseId() {
      this(null);
    }
}
