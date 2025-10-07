package pe.edu.upc.center.platform.profiles.domain.model.commands;

/**
 * Command to create a new profile with full name, age, and street address.
 */
public record CreateProfileCommand(String fullName, int age, String street) {
}
