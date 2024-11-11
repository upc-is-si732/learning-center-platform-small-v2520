package pe.edu.upc.center.platform.learning.domain.model.commands;

public record CreateCourseAssignCommand(
    String courseId,
    String section,
    Long professorId,
    String classroomId
) {
}
