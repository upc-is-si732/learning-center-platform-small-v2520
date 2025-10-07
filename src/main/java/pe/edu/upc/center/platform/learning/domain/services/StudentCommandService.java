package pe.edu.upc.center.platform.learning.domain.services;

import java.util.Optional;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.UpdateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Service interface for handling commands related to Student entities.
 */
public interface StudentCommandService {

  /**
   * Handles the creation of a new student based on the provided command.
   *
   * @param command the command containing the details of the student to be created
   * @return the StudentCode of the newly created student
   */
  StudentCode handle(CreateStudentCommand command);

  /**
   * Handles the update of a student based on the provided command.
   *
   * @param command the command containing the details of the student to be updated
   * @return an Optional containing the updated Student if the update was successful,
   *     or an empty Optional if not
   */
  Optional<Student> handle(UpdateStudentCommand command);

  /**
   * Handles the deletion of a student based on the provided command.
   *
   * @param command the command containing the details of the student to be deleted
   */
  void handle(DeleteStudentCommand command);
}
