package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseEnrollItemByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentQueryService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseEnrollItemRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;

/**
 * Implementation of the EnrollmentQueryService interface.
 */
@Service
public class EnrollmentQueryServiceImpl implements EnrollmentQueryService {

  private final EnrollmentRepository enrollmentRepository;
  private final CourseEnrollItemRepository courseEnrollItemRepository;

  /**
   * Constructor for EnrollmentQueryServiceImpl.
   *
   * @param enrollmentRepository the repository for Enrollment entities
   * @param courseEnrollItemRepository the repository for CourseEnrollItem entities
   */
  public EnrollmentQueryServiceImpl(EnrollmentRepository enrollmentRepository,
                                    CourseEnrollItemRepository courseEnrollItemRepository) {
    this.enrollmentRepository = enrollmentRepository;
    this.courseEnrollItemRepository = courseEnrollItemRepository;
  }

  @Override
  public Optional<Enrollment> handle(GetEnrollmentByIdQuery query) {
    return this.enrollmentRepository.findById(query.enrollmentId());
  }

  @Override
  public List<Enrollment> handle(GetAllEnrollmentsQuery query) {
    return this.enrollmentRepository.findAll();
  }

  @Override
  public Optional<CourseEnrollItem> handle(GetCourseEnrollItemByIdQuery query) {
    return this.courseEnrollItemRepository.findById(query.courseEnrollItemId());
  }
}
