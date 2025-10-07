package pe.edu.upc.center.platform.learning.domain.model.commands;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Command to update a student.
 *
 * @param studentCode the code of the student
 * @param fullName    the full name of the student
 * @param age         the age of the student
 * @param street      the street of the student
 */
public record UpdateStudentCommand(StudentCode studentCode, String fullName, int age,
                                   String street) {
}
