package pe.edu.upc.center.platform.learning.domain.model.commands;

import java.util.List;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Command to create a new enrollment.
 *
 * @param studentCode   the code of the student
 * @param period        the enrollment period
 * @param status        the status of the enrollment
 * @param courseEnrolls the list of course enroll commands
 */
public record CreateEnrollmentCommand(StudentCode studentCode, String period, String status,
                                      List<CreateCourseEnrollCommand> courseEnrolls) {
}
