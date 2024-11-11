package pe.edu.upc.center.platform.learning.domain.model.commands;

public record CreateStudentCommand(String fullName, int age, String street,
                                   Long programId, String startPeriod) {
}
