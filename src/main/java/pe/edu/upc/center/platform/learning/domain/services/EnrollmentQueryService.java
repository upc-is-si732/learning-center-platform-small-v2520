package pe.edu.upc.center.platform.learning.domain.services;

import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseEnrollItemByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;

import java.util.List;
import java.util.Optional;

public interface EnrollmentQueryService {
  Optional<Enrollment> handle(GetEnrollmentByIdQuery query);
  List<Enrollment> handle(GetAllEnrollmentsQuery query);
  Optional<CourseEnrollItem> handle(GetCourseEnrollItemByIdQuery query);

}
