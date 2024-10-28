package pe.edu.upc.center.platform.profiles.domain.model.commands;

public record CreateProfileCommand(String fullName, int age, String street) {
}
