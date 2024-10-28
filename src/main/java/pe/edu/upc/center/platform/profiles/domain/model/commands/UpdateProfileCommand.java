package pe.edu.upc.center.platform.profiles.domain.model.commands;

public record UpdateProfileCommand(Long profileId, String fullName, int age, String street) {
}
