package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollItemResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollResource;

/**
 * Assembler class to convert CourseEnrollItem entities to CourseEnrollItemResource representations.
 */
public class CourseEnrollItemResourceFromEntityAssembler {

  /**
   * Converts a CourseEnrollItem entity to a CourseEnrollItemResource.
   *
   * @param entity the CourseEnrollItem entity to convert
   * @return the corresponding CourseEnrollItemResource
   */
  public static CourseEnrollItemResource toResource(CourseEnrollItem entity) {
    return new CourseEnrollItemResource(
        entity.getId(),
        entity.getEnrollment().getId(),
        entity.getCourseAssign().getId(),
        entity.getNumberOfTimes()
    );
  }
}
