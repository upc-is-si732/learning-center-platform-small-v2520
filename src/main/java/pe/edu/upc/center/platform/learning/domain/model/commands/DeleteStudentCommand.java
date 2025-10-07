package pe.edu.upc.center.platform.learning.domain.model.commands;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Command to delete a student identified by their student code.
 *
 * @param studentCode The unique code of the student to be deleted.
 */
public record DeleteStudentCommand(StudentCode studentCode) {
}
