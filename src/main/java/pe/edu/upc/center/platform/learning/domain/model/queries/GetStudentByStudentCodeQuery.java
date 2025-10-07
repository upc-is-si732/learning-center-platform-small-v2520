package pe.edu.upc.center.platform.learning.domain.model.queries;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Query to get a student by their student code.
 *
 * @param studentCode The unique code of the student.
 */
public record GetStudentByStudentCodeQuery(StudentCode studentCode) {
}
