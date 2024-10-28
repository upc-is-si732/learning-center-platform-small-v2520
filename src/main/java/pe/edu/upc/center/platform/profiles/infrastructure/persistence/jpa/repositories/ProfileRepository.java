package pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
  boolean existsByFullName(String fullName);
  boolean existsByFullNameAndIdIsNot(String fullName, Long id);
  Optional<Profile> findByFullName(String fullName);
  List<Profile> findByAge(int age);
}
