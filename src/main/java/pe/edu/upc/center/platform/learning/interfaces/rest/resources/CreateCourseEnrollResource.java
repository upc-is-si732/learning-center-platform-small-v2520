package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource representing a request to create a course enrollment.
 *
 * @param courseAssignId the ID of the course assignment
 * @param numberOfTimes  the number of times to enroll
 */
public record CreateCourseEnrollResource(Long courseAssignId, int numberOfTimes) {
}
