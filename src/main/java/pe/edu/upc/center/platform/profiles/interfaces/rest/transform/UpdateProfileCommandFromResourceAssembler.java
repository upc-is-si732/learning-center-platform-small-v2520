package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
  public static UpdateProfileCommand toCommandFromResource(Long profileId, ProfileResource resource) {
    return new UpdateProfileCommand(profileId, resource.fullName(), resource.age(), resource.street());
  }
}
