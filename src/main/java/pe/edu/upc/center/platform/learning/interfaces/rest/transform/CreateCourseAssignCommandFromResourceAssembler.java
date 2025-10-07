package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseAssignResource;

/**
 * Assembler class to convert CreateCourseAssignResource to CreateCourseAssignCommand.
 */
public class CreateCourseAssignCommandFromResourceAssembler {

  /**
   * Converts a CreateCourseAssignResource to a CreateCourseAssignCommand.
   *
   * @param resource the resource containing course assignment details
   * @return the command constructed from the resource
   */
  public static CreateCourseAssignCommand toCommand(CreateCourseAssignResource resource) {
    return new CreateCourseAssignCommand(
        resource.courseId(), resource.section(), resource.professorId(), resource.classroomId()
    );
  }
}
