package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

import java.util.List;

public record EnrollmentResource(Long id, String studentCode, String period, String status,
                                 List<CourseEnrollResource> courseEnrolls) {
}
