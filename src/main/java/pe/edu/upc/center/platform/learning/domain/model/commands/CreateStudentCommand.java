package pe.edu.upc.center.platform.learning.domain.model.commands;

/**
 * Command to create a new student.
 *
 * @param fullName    the full name of the student
 * @param age         the age of the student
 * @param street      the street address of the student
 * @param programId   the ID of the program the student is enrolling in
 * @param startPeriod the start period for the student's enrollment
 */
public record CreateStudentCommand(String fullName, int age, String street,
                                   Long programId, String startPeriod) {
}
