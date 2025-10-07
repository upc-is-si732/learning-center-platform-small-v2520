package pe.edu.upc.center.platform.learning.domain.services;

import java.util.List;
import java.util.Optional;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseEnrollItemByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;

/**
 * Service interface for handling queries related to Enrollment and CourseEnrollItem entities.
 */
public interface EnrollmentQueryService {

  /**
   * Handles the GetEnrollmentByIdQuery to retrieve an Enrollment by its ID.
   *
   * @param query the query containing the ID of the enrollment to retrieve
   * @return an Optional containing the Enrollment if found, or empty if not found
   */
  Optional<Enrollment> handle(GetEnrollmentByIdQuery query);

  /**
   * Handles the GetAllEnrollmentsQuery to retrieve all Enrollment entities.
   *
   * @param query the query to retrieve all enrollments
   * @return a list of all Enrollment entities
   */
  List<Enrollment> handle(GetAllEnrollmentsQuery query);

  /**
   * Handles the GetCourseEnrollItemByIdQuery to retrieve a CourseEnrollItem by its ID.
   *
   * @param query the query containing the ID of the CourseEnrollItem to retrieve
   * @return an Optional containing the CourseEnrollItem if found, or empty if not found
   */
  Optional<CourseEnrollItem> handle(GetCourseEnrollItemByIdQuery query);

}
