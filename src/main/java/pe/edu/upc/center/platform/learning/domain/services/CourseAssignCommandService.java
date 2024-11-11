package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;

public interface CourseAssignCommandService {
  Long handle(CreateCourseAssignCommand command);
}
