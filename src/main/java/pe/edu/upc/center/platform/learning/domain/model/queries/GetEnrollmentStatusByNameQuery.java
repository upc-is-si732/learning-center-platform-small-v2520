package pe.edu.upc.center.platform.learning.domain.model.queries;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;

public record GetEnrollmentStatusByNameQuery(EnrollmentStatuses name) {
}
