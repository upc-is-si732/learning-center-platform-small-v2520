package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

/**
 * Assembler class to convert ProfileResource DTOs to UpdateProfileCommand.
 */
public class UpdateProfileCommandFromResourceAssembler {

  /**
   * Converts a ProfileResource DTO to an UpdateProfileCommand.
   *
   * @param profileId the ID of the profile to update
   * @param resource  the ProfileResource DTO containing updated profile data
   * @return the corresponding UpdateProfileCommand
   */
  public static UpdateProfileCommand toCommandFromResource(Long profileId,
                                                           ProfileResource resource) {
    return new UpdateProfileCommand(profileId, resource.fullName(), resource.age(),
        resource.street());
  }
}
