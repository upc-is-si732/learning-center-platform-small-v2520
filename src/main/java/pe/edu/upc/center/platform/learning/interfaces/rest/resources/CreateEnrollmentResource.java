package pe.edu.upc.center.platform.learning.interfaces.rest.resources;

import java.util.List;

public record CreateEnrollmentResource(String studentCode, String period, String status,
                                       List<CreateCourseEnrollResource> courseEnrolls) {
}
