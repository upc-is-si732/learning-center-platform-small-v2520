package pe.edu.upc.center.platform.profiles.domain.model.commands;

/**
 * Command to delete a profile by its ID.
 *
 * @param profileId the ID of the profile to be deleted
 */
public record DeleteProfileCommand(Long profileId) {
}
