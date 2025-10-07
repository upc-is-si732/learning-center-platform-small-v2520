package pe.edu.upc.center.platform.learning.domain.model.queries;

/**
 * Query to get a student by their ID.
 *
 * @param studentId the ID of the student to retrieve
 */
public record GetStudentByIdQuery(Long studentId) {
}
