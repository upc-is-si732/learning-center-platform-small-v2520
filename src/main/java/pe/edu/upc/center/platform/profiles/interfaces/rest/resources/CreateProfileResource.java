package pe.edu.upc.center.platform.profiles.interfaces.rest.resources;

/**
 * Resource representing the data required to create a new profile.
 *
 * @param fullName the full name of the profile
 * @param age      the age of the profile
 * @param street   the street address of the profile
 */
public record CreateProfileResource(String fullName, int age, String street) {
}

