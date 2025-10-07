package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateProfileResource;

/**
 * Assembler class to convert CreateProfileResource to CreateProfileCommand.
 */
public class CreateProfileCommandFromResourceAssembler {

  /**
   * Converts a CreateProfileResource to a CreateProfileCommand.
   *
   * @param resource the resource containing profile details
   * @return a command object for creating a profile
   */
  public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
    return new CreateProfileCommand(resource.fullName(), resource.age(), resource.street());
  }
}
