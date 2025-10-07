package pe.edu.upc.center.platform.learning.domain.model.queries;

/**
 * Query to get an enrollment by its ID.
 *
 * @param enrollmentId the ID of the enrollment to retrieve
 */
public record GetEnrollmentByIdQuery(Long enrollmentId) {
}
