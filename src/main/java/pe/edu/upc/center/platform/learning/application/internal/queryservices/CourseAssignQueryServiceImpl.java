package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllCourseAssignsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseAssignByIdQuery;
import pe.edu.upc.center.platform.learning.domain.services.CourseAssignQueryService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseAssignRepository;

/**
 * Implementation of the CourseAssignQueryService interface.
 */
@Service
public class CourseAssignQueryServiceImpl implements CourseAssignQueryService {

  private final CourseAssignRepository courseAssignRepository;

  /**
   * Constructor for CourseAssignQueryServiceImpl.
   *
   * @param courseAssignRepository the repository for CourseAssign entities
   */
  public CourseAssignQueryServiceImpl(CourseAssignRepository courseAssignRepository) {
    this.courseAssignRepository = courseAssignRepository;
  }

  @Override
  public Optional<CourseAssign> handle(GetCourseAssignByIdQuery query) {
    return this.courseAssignRepository.findById(query.courseAssignId());
  }

  @Override
  public List<CourseAssign> handle(GetAllCourseAssignsQuery query) {
    return this.courseAssignRepository.findAll();
  }
}
