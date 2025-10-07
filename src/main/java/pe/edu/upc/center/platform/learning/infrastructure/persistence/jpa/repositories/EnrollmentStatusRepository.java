package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;

/**
 * Repository interface for EnrollmentStatus entities.
 */
public interface EnrollmentStatusRepository extends JpaRepository<EnrollmentStatus, Long> {

  /**
   * Checks if an EnrollmentStatus with the given name exists.
   *
   * @param name the name of the EnrollmentStatus
   * @return true if an EnrollmentStatus with the given name exists, false otherwise
   */
  boolean existsByName(EnrollmentStatuses name);

  /**
   * Finds an EnrollmentStatus by its name.
   *
   * @param name the name of the EnrollmentStatus
   * @return an Optional containing the found EnrollmentStatus, or empty if not found
   */
  Optional<EnrollmentStatus> findByName(EnrollmentStatuses name);
}
