package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateStudentResource;

/**
 * Assembler to convert CreateStudentResource to CreateStudentCommand.
 */
public class CreateStudentCommandFromResourceAssembler {

  /**
   * Convert CreateStudentResource to CreateStudentCommand.
   *
   * @param resource CreateStudentResource
   * @return CreateStudentCommand
   */
  public static CreateStudentCommand toCommandFromResource(CreateStudentResource resource) {
    return new CreateStudentCommand(resource.name(), resource.age(), resource.address(),
        resource.programId(), resource.startPeriod());
  }
}
