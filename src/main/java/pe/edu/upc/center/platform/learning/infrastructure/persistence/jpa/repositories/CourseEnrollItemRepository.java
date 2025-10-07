package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;

/**
 * Repository interface for CourseEnrollItem entities.
 */
@Repository
public interface CourseEnrollItemRepository extends JpaRepository<CourseEnrollItem, Long> {

  /**
   * Checks if a CourseEnrollItem exists for the given Enrollment and CourseAssign.
   *
   * @param enrollment the Enrollment entity
   * @param courseAssign the CourseAssign entity
   * @return true if a CourseEnrollItem exists for the given Enrollment and CourseAssign,
   *     false otherwise
   */
  boolean existsByEnrollmentAndCourseAssign(Enrollment enrollment, CourseAssign courseAssign);
}
