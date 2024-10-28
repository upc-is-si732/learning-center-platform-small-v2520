package pe.edu.upc.center.platform.learning.domain.model.commands;

import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

public record DeleteStudentCommand(StudentCode studentCode) {
}
