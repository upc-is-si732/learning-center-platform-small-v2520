package pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;

/**
 * Repository interface for managing Student entities.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  /**
   * Find a student by their student code.
   *
   * @param studentCode the student code to search for
   * @return an Optional containing the Student if found, or empty if not found
   */
  Optional<Student> findByStudentCode(StudentCode studentCode);

  /**
   * Find a student by their profile ID.
   *
   * @param profileId the profile ID to search for
   * @return an Optional containing the Student if found, or empty if not found
   */
  Optional<Student> findByProfileId(ProfileId profileId);

  /**
   * Check if a student exists by their student code.
   *
   * @param studentCode the student code to check
   * @return true if a student with the given student code exists, false otherwise
   */
  boolean existsByStudentCode(StudentCode studentCode);
}
