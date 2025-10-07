package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.commands.SeedEnrollmentStatusesCommand;

/**
 * Service interface for handling commands related to enrollment statuses.
 */
public interface EnrollmentStatusCommandService {

  /** Handles the seeding of enrollment statuses.
   *
   * @param command the command containing the details for seeding enrollment statuses
   */
  void handle(SeedEnrollmentStatusesCommand command);
}
