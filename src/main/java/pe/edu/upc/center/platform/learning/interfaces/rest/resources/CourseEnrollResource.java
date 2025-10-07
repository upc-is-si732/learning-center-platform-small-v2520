package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource representing a course enrollment.
 *
 * @param id              the ID of the course enrollment
 * @param courseAssignId  the ID of the assigned course
 * @param numberOfTimes   the number of times the course has been enrolled
 */
public record CourseEnrollResource(Long id, Long courseAssignId, int numberOfTimes) {
}
