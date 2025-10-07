package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource class for creating a course assignment.
 *
 * @param courseId    the ID of the course
 * @param section     the section of the course
 * @param professorId the ID of the professor
 * @param classroomId the ID of the classroom
 */
public record CreateCourseAssignResource(
    String courseId,
    String section,
    Long professorId,
    String classroomId
) {
}
