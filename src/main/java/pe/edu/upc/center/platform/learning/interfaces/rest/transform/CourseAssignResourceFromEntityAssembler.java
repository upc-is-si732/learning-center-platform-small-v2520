package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseAssignResource;

public class CourseAssignResourceFromEntityAssembler {
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
