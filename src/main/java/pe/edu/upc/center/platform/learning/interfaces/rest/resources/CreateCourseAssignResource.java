package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

public record CreateCourseAssignResource(
    String courseId,
    String section,
    Long professorId,
    String classroomId
) {
}
