package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.UpdateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.StudentResource;

/**
 * Assembler to convert StudentResource and studentCode into UpdateStudentCommand.
 */
public class UpdateStudentCommandFromResourceAssembler {

  /**
   * Converts a StudentResource and studentCode into an UpdateStudentCommand.
   *
   * @param studentCode the unique code of the student to be updated
   * @param resource    the resource containing updated student details
   * @return the corresponding UpdateStudentCommand
   */
  public static UpdateStudentCommand toCommandFromResource(String studentCode,
                                                           StudentResource resource) {
    return new UpdateStudentCommand(new StudentCode(studentCode), resource.name(),
        resource.age(), resource.address());
  }
}
