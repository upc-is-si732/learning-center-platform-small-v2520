package pe.edu.upc.center.platform.learning.domain.model.queries;

/**
 * Query to get a course enroll item by its ID.
 *
 * @param courseEnrollItemId the ID of the course enroll item
 */
public record GetCourseEnrollItemByIdQuery(Long courseEnrollItemId) {
}
