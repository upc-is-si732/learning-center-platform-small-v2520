package pe.edu.upc.center.platform.iam.interfaces.rest.transform;

import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.center.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * AuthenticatedUserResourceFromEntityAssembler class.
 * This class is used to convert a User entity to an AuthenticatedUserResource.
 */
public class AuthenticatedUserResourceFromEntityAssembler {

  /**
   * Convert a User entity to an AuthenticatedUserResource.
   *
   * @param user  the User entity to convert
   * @param token the token of the authenticated user
   * @return the AuthenticatedUserResource
   */
  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
    return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
  }
}
