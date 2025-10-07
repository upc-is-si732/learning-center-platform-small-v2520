package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

import java.util.List;

/**
 * Resource representing a request to create an enrollment.
 *
 * @param studentCode   the code of the student
 * @param period        the enrollment period
 * @param status        the status of the enrollment
 * @param courseEnrolls the list of course enrollments associated with this enrollment
 */
public record CreateEnrollmentResource(String studentCode, String period, String status,
                                       List<CreateCourseEnrollResource> courseEnrolls) {
}
