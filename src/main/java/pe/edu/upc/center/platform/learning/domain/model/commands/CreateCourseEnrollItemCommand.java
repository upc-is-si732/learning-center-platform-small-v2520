package pe.edu.upc.center.platform.learning.domain.model.commands;

/**
 * Command to create a course enrollment item.
 *
 * @param enrollmentId   the ID of the enrollment
 * @param courseAssignId the ID of the course assignment
 * @param numberOfTimes  the number of times the course is enrolled
 */
public record CreateCourseEnrollItemCommand(Long enrollmentId, Long courseAssignId,
                                            int numberOfTimes) {
}
