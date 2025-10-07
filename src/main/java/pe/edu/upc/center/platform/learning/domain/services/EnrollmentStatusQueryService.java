package pe.edu.upc.center.platform.learning.domain.services;

import java.util.List;
import java.util.Optional;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentStatusesQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentStatusByNameQuery;

/**
 * Service interface for handling queries related to EnrollmentStatus entities.
 */
public interface EnrollmentStatusQueryService {

  /**
   * Handle the query to get all enrollment statuses.
   *
   * @param query The query to retrieve all enrollment statuses.
   * @return A list of all EnrollmentStatus entities.
   */
  List<EnrollmentStatus> handle(GetAllEnrollmentStatusesQuery query);

  /**
   * Handle the query to get an enrollment status by its name.
   *
   * @param query The query containing the name of the enrollment status to retrieve.
   * @return An Optional containing the EnrollmentStatus if found, or empty if not found.
   */
  Optional<EnrollmentStatus> handle(GetEnrollmentStatusByNameQuery query);
}
