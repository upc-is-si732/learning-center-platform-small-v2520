package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseAssignResource;

public class CreateCourseAssignCommandFromResourceAssembler {
  public static CreateCourseAssignCommand toCommand(CreateCourseAssignResource resource) {
    return new CreateCourseAssignCommand(
        resource.courseId(), resource.section(), resource.professorId(), resource.classroomId()
    );
  }
}
