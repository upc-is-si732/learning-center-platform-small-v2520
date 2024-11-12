package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

public record CourseEnrollItemResource(Long id, Long enrollmentId, Long courseAssignId, int numberOfTimes) {
}
