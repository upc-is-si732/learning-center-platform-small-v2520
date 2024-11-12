package pe.edu.upc.center.platform.iam.domain.model.queries;

import pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
