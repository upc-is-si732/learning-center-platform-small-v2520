package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollResource;

/**
 * Assembler class for converting CourseEnrollItem entities to CourseEnrollResource representations.
 */
public class CourseEnrollResourceFromEntityAssembler {

  /**
   * Converts a CourseEnrollItem entity to a CourseEnrollResource.
   *
   * @param entity the CourseEnrollItem entity to convert
   * @return the corresponding CourseEnrollResource
   */
  public static CourseEnrollResource toResource(CourseEnrollItem entity) {
    return new CourseEnrollResource(
        entity.getId(),
        entity.getCourseAssign().getId(),
        entity.getNumberOfTimes()
    );
  }
}
