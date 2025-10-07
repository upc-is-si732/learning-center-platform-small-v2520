package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

/**
 * Resource class representing the data required to create a new Student.
 *
 * @param name       the name of the student
 * @param age        the age of the student
 * @param address    the address of the student
 * @param programId  the ID of the program the student is enrolled in
 * @param startPeriod the start period of the student's enrollment
 */
public record CreateStudentResource(String name, int age, String address,
                                    Long programId, String startPeriod) {
}
