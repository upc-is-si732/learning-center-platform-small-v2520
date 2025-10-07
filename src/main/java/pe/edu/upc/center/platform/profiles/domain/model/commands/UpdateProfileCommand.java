package pe.edu.upc.center.platform.profiles.domain.model.commands;

/**
 * Command to update an existing user profile.
 *
 * @param profileId The ID of the profile to update.
 * @param fullName  The new full name of the user.
 * @param age       The new age of the user.
 * @param street    The new street address of the user.
 */
public record UpdateProfileCommand(Long profileId, String fullName, int age, String street) {
}
