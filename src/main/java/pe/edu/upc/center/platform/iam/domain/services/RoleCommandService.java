package pe.edu.upc.center.platform.iam.domain.services;

import pe.edu.upc.center.platform.iam.domain.model.commands.SeedRolesCommand;

/**
 * Service interface for handling role-related commands in the IAM domain.
 */
public interface RoleCommandService {

  /** Handles the seeding of roles in the system.
   *
   * @param command The command containing the details for seeding roles.
   */
  void handle(SeedRolesCommand command);
}
