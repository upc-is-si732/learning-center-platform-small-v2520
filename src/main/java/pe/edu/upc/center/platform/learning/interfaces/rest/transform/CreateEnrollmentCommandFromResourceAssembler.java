package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateEnrollmentResource;

/**
 * Assembler class to convert CreateEnrollmentResource to CreateEnrollmentCommand.
 */
public class CreateEnrollmentCommandFromResourceAssembler {

  /**
   * Converts a CreateEnrollmentResource to a CreateEnrollmentCommand.
   *
   * @param resource the resource to convert
   * @return the corresponding command
   */
  public static CreateEnrollmentCommand toCommand(CreateEnrollmentResource resource) {
    var createCourseEnrollResource = resource.courseEnrolls().stream()
        .map(CreateCourseEnrollCommandFromResourceAssembler::toCommand)
        .toList();

    return new CreateEnrollmentCommand(new StudentCode(resource.studentCode()), resource.period(),
        resource.status(), createCourseEnrollResource);
  }
}
