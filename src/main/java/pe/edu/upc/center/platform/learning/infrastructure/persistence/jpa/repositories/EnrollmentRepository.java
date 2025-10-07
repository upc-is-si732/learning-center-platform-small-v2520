package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Repository interface for Enrollment entities.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

  /**
   * Checks if an Enrollment exists for the given StudentCode and period.
   *
   * @param studentCode the StudentCode value object
   * @param period the period string
   * @return true if an Enrollment exists for the given StudentCode and period, false otherwise
   */
  boolean existsByStudentCodeAndAndPeriod(StudentCode studentCode, String period);
}
