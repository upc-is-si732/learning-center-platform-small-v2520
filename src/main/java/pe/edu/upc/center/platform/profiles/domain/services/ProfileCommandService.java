package pe.edu.upc.center.platform.profiles.domain.services;

import java.util.Optional;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;

/**
 * Service interface for handling profile-related commands.
 */
public interface ProfileCommandService {

  /**
   * Handles the creation of a new profile based on the provided command.
   *
   * @param command the command containing the profile information
   * @return the ID of the newly created profile
   */
  Long handle(CreateProfileCommand command);

  /**
   * Handles the update of a profile based on the provided command.
   *
   * @param command the command containing the updated profile information
   * @return an Optional containing the updated Profile if successful, or empty if not found
   */
  Optional<Profile> handle(UpdateProfileCommand command);

  /**
   * Handles the deletion of a profile based on the provided command.
   *
   * @param command the command containing the ID of the profile to be deleted
   */
  void handle(DeleteProfileCommand command);
}
