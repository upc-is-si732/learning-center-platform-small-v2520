package pe.edu.upc.center.platform.iam.domain.model.queries;

import pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles;

/**
 * Query to get a role by its name.
 *
 * @param name the name of the role
 */
public record GetRoleByNameQuery(Roles name) {
}
