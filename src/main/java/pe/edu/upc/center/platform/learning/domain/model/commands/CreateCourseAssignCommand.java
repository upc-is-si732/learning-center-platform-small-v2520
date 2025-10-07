package pe.edu.upc.center.platform.learning.domain.model.commands;

/**
 * Command to create a course assignment.
 *
 * @param courseId    the ID of the course
 * @param section     the section of the course
 * @param professorId the ID of the professor assigned to the course
 * @param classroomId the ID of the classroom where the course will be held
 */
public record CreateCourseAssignCommand(
    String courseId,
    String section,
    Long professorId,
    String classroomId
) {
}
