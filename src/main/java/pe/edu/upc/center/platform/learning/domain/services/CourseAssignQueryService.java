package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseAssignByIdQuery;

import java.util.Optional;

public interface CourseAssignQueryService {
  Optional<CourseAssign> handle(GetCourseAssignByIdQuery query);
}
