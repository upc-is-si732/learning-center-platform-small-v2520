package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;

import java.util.Optional;

public interface EnrollmentStatusRepository extends JpaRepository<EnrollmentStatus, Long> {
  boolean existsByName(EnrollmentStatuses name);
  Optional<EnrollmentStatus> findByName(EnrollmentStatuses name);
}
