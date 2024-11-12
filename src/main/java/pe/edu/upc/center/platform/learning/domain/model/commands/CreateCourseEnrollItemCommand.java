package pe.edu.upc.center.platform.learning.domain.model.commands;

public record CreateCourseEnrollItemCommand(Long enrollmentId, Long courseAssignId, int numberOfTimes) {
}
