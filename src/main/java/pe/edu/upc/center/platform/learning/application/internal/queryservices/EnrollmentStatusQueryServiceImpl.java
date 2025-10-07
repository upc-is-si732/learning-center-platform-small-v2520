package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentStatusesQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentStatusByNameQuery;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentStatusQueryService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentStatusRepository;

/**
 * Service implementation for querying EnrollmentStatus entities.
 */
@Service
public class EnrollmentStatusQueryServiceImpl implements EnrollmentStatusQueryService {

  private final EnrollmentStatusRepository enrollmentStatusRepository;

  /**
   * Constructs a new EnrollmentStatusQueryServiceImpl with the specified
   *     EnrollmentStatusRepository.
   *
   * @param enrollmentStatusRepository the repository for EnrollmentStatus entities
   */
  public EnrollmentStatusQueryServiceImpl(EnrollmentStatusRepository enrollmentStatusRepository) {
    this.enrollmentStatusRepository = enrollmentStatusRepository;
  }

  @Override
  public List<EnrollmentStatus> handle(GetAllEnrollmentStatusesQuery query) {
    return this.enrollmentStatusRepository.findAll();
  }

  @Override
  public Optional<EnrollmentStatus> handle(GetEnrollmentStatusByNameQuery query) {
    return this.enrollmentStatusRepository.findByName(query.name());
  }
}
