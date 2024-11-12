package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;

public class CreateCourseEnrollItemCommandFromResourceAssembler {

    public static CreateCourseEnrollItemCommand toCommand(Long enrollmentId,
                                                          CreateCourseEnrollResource resource) {
      return new CreateCourseEnrollItemCommand(
          enrollmentId,
          resource.courseAssignId(),
          resource.numberOfTimes()
      );
    }
}
