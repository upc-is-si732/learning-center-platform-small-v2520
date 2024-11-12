package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;

public class CreateCourseEnrollCommandFromResourceAssembler {
  public static CreateCourseEnrollCommand toCommand(CreateCourseEnrollResource resource) {
    return new CreateCourseEnrollCommand(resource.courseAssignId(), resource.numberOfTimes());
  }
}
