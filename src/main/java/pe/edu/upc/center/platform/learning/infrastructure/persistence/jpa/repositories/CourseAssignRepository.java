package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseId;

/**
 * Repository interface for CourseAssign entities.
 */
@Repository
public interface CourseAssignRepository extends JpaRepository<CourseAssign, Long> {

  /**
   * Checks if a CourseAssign exists by course ID and section.
   *
   * @param courseId the ID of the course
   * @param section the section of the course
   * @return true if a CourseAssign with the given course ID and section exists, false otherwise
   */
  boolean existsByCourseIdAndSection(CourseId courseId, String section);
}
