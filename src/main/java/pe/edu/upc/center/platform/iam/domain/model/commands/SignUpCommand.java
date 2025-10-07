package pe.edu.upc.center.platform.iam.domain.model.commands;

import java.util.List;
import pe.edu.upc.center.platform.iam.domain.model.entities.Role;

/**
 * Command to sign up a new user with specified username, password, and roles.
 *
 * @param username the username of the new user
 * @param password the password of the new user
 * @param roles    the list of roles assigned to the new user
 */
public record SignUpCommand(String username, String password, List<Role> roles) {
}
