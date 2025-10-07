package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseAssignResource;

/**
 * Assembler class to convert CourseAssign entities to CourseAssignResource representations.
 */
public class CourseAssignResourceFromEntityAssembler {

  /** Converts a CourseAssign entity to a CourseAssignResource.
   *
   * @param entity the CourseAssign entity to convert
   * @return the corresponding CourseAssignResource
   */
  public static CourseAssignResource toResource(CourseAssign entity) {

    return new CourseAssignResource(
        entity.getId(),
        entity.getCourseId(),
        entity.getSection(),
        entity.getProfessorId(),
        entity.getClassroomId()
    );
  }
}
