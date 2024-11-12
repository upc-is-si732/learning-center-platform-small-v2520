package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.commands.SeedEnrollmentStatusesCommand;

public interface EnrollmentStatusCommandService {
  void handle(SeedEnrollmentStatusesCommand command);
}
