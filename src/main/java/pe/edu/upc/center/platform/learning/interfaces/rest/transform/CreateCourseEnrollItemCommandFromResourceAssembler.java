package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;

/**
 * Assembler class to convert CreateCourseEnrollResource to CreateCourseEnrollItemCommand.
 */
public class CreateCourseEnrollItemCommandFromResourceAssembler {

  /** Converts a CreateCourseEnrollResource to a CreateCourseEnrollItemCommand.
   *
   * @param enrollmentId the ID of the enrollment
   * @param resource     the resource containing course enrollment details
   * @return a command object for creating a course enrollment item
   */
  public static CreateCourseEnrollItemCommand toCommand(Long enrollmentId,
                                                        CreateCourseEnrollResource resource) {
    return new CreateCourseEnrollItemCommand(
        enrollmentId,
        resource.courseAssignId(),
        resource.numberOfTimes()
    );
  }
}
