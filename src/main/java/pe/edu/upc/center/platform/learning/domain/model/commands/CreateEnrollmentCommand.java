package pe.edu.upc.center.platform.learning.domain.model.commands;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

import java.util.List;

public record CreateEnrollmentCommand(StudentCode studentCode, String period, String status,
                                      List<CreateCourseEnrollCommand> courseEnrolls) {
}
