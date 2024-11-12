package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollResource;

public class CourseEnrollResourceFromEntityAssembler {

  public static CourseEnrollResource toResource(CourseEnrollItem entity) {
    return new CourseEnrollResource(
        entity.getId(),
        entity.getCourseAssign().getId(),
        entity.getNumberOfTimes()
    );
  }
}
