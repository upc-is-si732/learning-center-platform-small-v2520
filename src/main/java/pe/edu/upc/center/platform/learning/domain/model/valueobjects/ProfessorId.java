package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProfessorId(Long professorId) {
    public ProfessorId {
      if (professorId < 0) {
        throw new IllegalArgumentException("Professor professorId cannot be negative");
      }
    }

    public ProfessorId() {
      this(0L);
    }
}
