package pe.edu.upc.center.platform.iam.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.iam.domain.model.entities.Role;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetRoleByNameQuery;
import pe.edu.upc.center.platform.iam.domain.services.RoleQueryService;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

/**
 * RoleQueryServiceImpl.
 *
 * <p>This class implements the RoleQueryService interface and provides the implementation for
 *     handling role-related queries.
 * </p>
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {
  private final RoleRepository roleRepository;

  /**
   * Constructor for RoleQueryServiceImpl.
   *
   * @param roleRepository the role repository
   */
  public RoleQueryServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Handle the get all roles query.
   *
   * @param query the get all roles query
   * @return List of Role the list of roles
   */
  @Override
  public List<Role> handle(GetAllRolesQuery query) {
    return roleRepository.findAll();
  }

  /**
   * Handle the get role by name query.
   *
   * @param query the get role by name query
   * @return Optional of Role the role if found
   */
  @Override
  public Optional<Role> handle(GetRoleByNameQuery query) {
    return roleRepository.findByName(query.name());
  }
}
