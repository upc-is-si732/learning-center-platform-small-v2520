package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseId;

@Repository
public interface CourseAssignRepository extends JpaRepository<CourseAssign, Long> {
  boolean existsByCourseIdAndSection(CourseId courseId, String section);
}
