package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

import java.util.List;

/**
 * Resource representing an enrollment with its details.
 *
 * @param id            the unique identifier of the enrollment
 * @param studentCode   the code of the student associated with the enrollment
 * @param period        the academic period of the enrollment
 * @param status        the current status of the enrollment
 * @param courseEnrolls a list of course enrollments associated with this enrollment
 */
public record EnrollmentResource(Long id, String studentCode, String period, String status,
                                 List<CourseEnrollResource> courseEnrolls) {
}
