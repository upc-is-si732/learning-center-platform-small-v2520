package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource representation of a user profile.
 *
 * @param id       the unique identifier of the profile
 * @param fullName the full name of the user
 * @param age      the age of the user
 * @param street   the street address of the user
 */
public record ProfileResource(Long id, String fullName, int age, String street) {
}
