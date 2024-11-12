package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;

@Repository
public interface CourseEnrollItemRepository extends JpaRepository<CourseEnrollItem, Long> {
  boolean existsByEnrollmentAndCourseAssign(Enrollment enrollment, CourseAssign courseAssign);
}
