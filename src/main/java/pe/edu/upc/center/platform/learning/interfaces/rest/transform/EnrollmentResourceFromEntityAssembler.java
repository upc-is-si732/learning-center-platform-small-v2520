package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.EnrollmentResource;

/**
 * Assembler class to convert Enrollment entities to EnrollmentResource representations.
 */
public class EnrollmentResourceFromEntityAssembler {

  /**
   * Converts an Enrollment entity to an EnrollmentResource.
   *
   * @param enrollment the Enrollment entity to convert
   * @return the corresponding EnrollmentResource
   */
  public static EnrollmentResource toResource(Enrollment enrollment) {
    var courseEnrollResources = enrollment.getCourseEnroll().getCourseEnrollItems().stream()
        .map(CourseEnrollResourceFromEntityAssembler::toResource)
        .toList();
    return new EnrollmentResource(enrollment.getId(), enrollment.getStudentCode().studentCode(),
        enrollment.getPeriod(), enrollment.getStatus().getStringName(),
        courseEnrollResources);
  }
}
