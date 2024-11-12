package pe.edu.upc.center.platform.iam.domain.services;

import pe.edu.upc.center.platform.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
