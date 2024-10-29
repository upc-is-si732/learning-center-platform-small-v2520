package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProgramId(Long programId) {
    public ProgramId {
      if (programId < 0) {
        throw new IllegalArgumentException("Program programId cannot be negative");
      }
    }

    public ProgramId() {
      this(0L);
    }
}
