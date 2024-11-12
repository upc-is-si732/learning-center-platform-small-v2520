package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
  boolean existsByStudentCodeAndAndPeriod(StudentCode studentCode, String period);
}
