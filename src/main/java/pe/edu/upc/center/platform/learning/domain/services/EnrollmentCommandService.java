package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;

public interface EnrollmentCommandService {
  Long handle(CreateEnrollmentCommand command);
  Long handle(CreateCourseEnrollItemCommand command);
}
