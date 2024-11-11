package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

public record CourseAssignResource(
    Long id,
    String courseId,
    String section,
    Long professorId,
    String classroomId
) {
}
