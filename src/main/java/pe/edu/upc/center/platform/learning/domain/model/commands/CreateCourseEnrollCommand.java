package pe.edu.upc.center.platform.learning.domain.model.commands;

/**
 * Command to create a course enrollment.
 *
 * @param courseAssignId the ID of the course assignment
 * @param numberOfTimes  the number of times the course is to be enrolled
 */
public record CreateCourseEnrollCommand(Long courseAssignId, int numberOfTimes) {
}
