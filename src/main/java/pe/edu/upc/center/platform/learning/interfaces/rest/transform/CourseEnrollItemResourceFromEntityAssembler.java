package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollItemResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollResource;

public class CourseEnrollItemResourceFromEntityAssembler {

  public static CourseEnrollItemResource toResource(CourseEnrollItem entity) {
    return new CourseEnrollItemResource(
        entity.getId(),
        entity.getEnrollment().getId(),
        entity.getCourseAssign().getId(),
        entity.getNumberOfTimes()
    );
  }
}
