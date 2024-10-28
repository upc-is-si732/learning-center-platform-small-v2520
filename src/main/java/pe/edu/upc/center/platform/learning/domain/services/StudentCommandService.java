package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.UpdateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

import java.util.Optional;

public interface StudentCommandService {
  StudentCode handle(CreateStudentCommand command);
  Optional<Student> handle(UpdateStudentCommand command);
  void handle(DeleteStudentCommand command);
}
