package pe.edu.upc.center.platform.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.center.platform.iam.domain.services.RoleQueryService;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.RoleResource;
import pe.edu.upc.center.platform.iam.interfaces.rest.transform.RoleResourceFromEntityAssembler;

/**
 * REST controller for managing roles.
 */
@RestController
@RequestMapping(value = "/ap/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles", description = "Role Management Endpoints")
public class RolesController {

  private final RoleQueryService roleQueryService;

  /**
   * Constructor for RolesController.
   *
   * @param roleQueryService The RoleQueryService to be used by the controller.
   */
  public RolesController(RoleQueryService roleQueryService) {
    this.roleQueryService = roleQueryService;
  }

  /**
   * Get all roles.
   *
   * @return List of role resources
   * @see RoleResource
   */
  @GetMapping
  public ResponseEntity<List<RoleResource>> getAllRoles() {
    var getAllRolesQuery = new GetAllRolesQuery();
    var roles = roleQueryService.handle(getAllRolesQuery);
    var roleResources = roles.stream()
        .map(RoleResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(roleResources);
  }
}
