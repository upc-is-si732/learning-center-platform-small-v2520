package pe.edu.upc.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ClassroomId(String classroomId) {
    public ClassroomId {
        if (classroomId == null || classroomId.isBlank()) {
            throw new IllegalArgumentException("Classroom classroomId cannot be null or blank");
        }
    }

    public ClassroomId() {
        this(null);
    }
}
