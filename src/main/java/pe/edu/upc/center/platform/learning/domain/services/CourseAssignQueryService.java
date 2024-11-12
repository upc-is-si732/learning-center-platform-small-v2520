package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllCourseAssignsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseAssignByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CourseAssignQueryService {
  Optional<CourseAssign> handle(GetCourseAssignByIdQuery query);
  List<CourseAssign> handle(GetAllCourseAssignsQuery query);
}
