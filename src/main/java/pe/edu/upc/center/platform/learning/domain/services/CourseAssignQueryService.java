package pe.edu.upc.center.platform.learning.domain.services;

import java.util.List;
import java.util.Optional;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllCourseAssignsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseAssignByIdQuery;

/**
 * Service interface for querying CourseAssign entities.
 */
public interface CourseAssignQueryService {

  /**
   * Handles the retrieval of a CourseAssign by its ID.
   *
   * @param query the query containing the ID of the CourseAssign
   * @return an Optional containing the CourseAssign if found, or empty if not found
   */
  Optional<CourseAssign> handle(GetCourseAssignByIdQuery query);

  /**
   * Handles the retrieval of all CourseAssign entities.
   *
   * @param query the query to retrieve all CourseAssigns
   * @return a list of all CourseAssign entities
   */
  List<CourseAssign> handle(GetAllCourseAssignsQuery query);
}
