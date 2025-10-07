package pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;

/** Repository interface for managing Profile entities.
 *
 * <p>This interface extends JpaRepository to provide CRUD operations and custom query methods
 *     for the Profile entity.</p>
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  /** Custom query method to check existence of a profile by full name.
   *
   * @param fullName the full name to check for existence
   * @return true if a profile with the given full name exists, false otherwise
   */
  boolean existsByFullName(String fullName);

  /** Custom query method to check existence of a profile by full name excluding a specific ID.
   *
   * @param fullName the full name to check for existence
   * @param id the ID to exclude from the check
   * @return true if a profile with the given full name exists excluding the specified ID,
   *     false otherwise
   */
  boolean existsByFullNameAndIdIsNot(String fullName, Long id);

  /** Custom query method to find a profile by full name.
   *
   * @param fullName the full name of the profile to search for
   * @return an Optional containing the profile if found, or empty if not found
   */
  Optional<Profile> findByFullName(String fullName);

  /** Custom query method to find profiles by age.
   *
   * @param age the age to filter profiles
   * @return a list of profiles matching the specified age
   */
  List<Profile> findByAge(int age);
}
