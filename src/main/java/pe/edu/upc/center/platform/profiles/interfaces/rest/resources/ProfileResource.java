package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

/**
 * Resource representation of a user profile.
 *
 * @param id       The unique identifier of the profile.
 * @param fullName The full name of the profile owner.
 * @param age      The age of the profile owner.
 * @param street   The street address of the profile owner.
 */
public record ProfileResource(Long id, String fullName, int age, String street) {
}
