package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;

/**
 * Assembler class to convert CreateCourseEnrollResource to CreateCourseEnrollCommand.
 */
public class CreateCourseEnrollCommandFromResourceAssembler {

  /**
   * Converts a CreateCourseEnrollResource to a CreateCourseEnrollCommand.
   *
   * @param resource the resource containing course enrollment details
   * @return the command to create a course enrollment
   */
  public static CreateCourseEnrollCommand toCommand(CreateCourseEnrollResource resource) {
    return new CreateCourseEnrollCommand(resource.courseAssignId(), resource.numberOfTimes());
  }
}
