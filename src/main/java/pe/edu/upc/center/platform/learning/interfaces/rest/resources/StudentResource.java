package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource representation of a Student in the learning platform.
 *
 * @param id          The unique identifier of the student.
 * @param name        The full name of the student.
 * @param age         The age of the student.
 * @param address     The address of the student.
 * @param programId   The identifier of the program the student is enrolled in.
 * @param startPeriod The start period of the student's enrollment.
 */
public record StudentResource(String id, String name, int age, String address,
                              Long programId, String startPeriod) {
}
