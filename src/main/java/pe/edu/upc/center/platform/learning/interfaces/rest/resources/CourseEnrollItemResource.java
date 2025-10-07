package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource representation for a course enrollment item.
 *
 * @param id              the unique identifier of the course enrollment item
 * @param enrollmentId    the identifier of the associated enrollment
 * @param courseAssignId  the identifier of the assigned course
 * @param numberOfTimes   the number of times the course has been taken
 */
public record CourseEnrollItemResource(Long id, Long enrollmentId,
                                       Long courseAssignId, int numberOfTimes) {
}
