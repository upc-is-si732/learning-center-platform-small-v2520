package pe.edu.upc.center.platform.learning.domain.model.queries;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;

/**
 * Query to get an enrollment status by its name.
 *
 * @param name the name of the enrollment status
 */
public record GetEnrollmentStatusByNameQuery(EnrollmentStatuses name) {
}
