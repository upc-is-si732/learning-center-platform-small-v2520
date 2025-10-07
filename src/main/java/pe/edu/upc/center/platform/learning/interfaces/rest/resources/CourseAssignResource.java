package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource representation for a course assignment.
 *
 * @param id           the unique identifier of the course assignment
 * @param courseId     the identifier of the course
 * @param section      the section of the course
 * @param professorId  the identifier of the assigned professor
 * @param classroomId  the identifier of the assigned classroom
 */
public record CourseAssignResource(
    Long id,
    String courseId,
    String section,
    Long professorId,
    String classroomId
) {
}
