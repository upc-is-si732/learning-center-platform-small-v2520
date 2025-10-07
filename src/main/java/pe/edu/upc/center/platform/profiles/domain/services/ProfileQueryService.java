package pe.edu.upc.center.platform.profiles.domain.services;

import java.util.List;
import java.util.Optional;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByAgeQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByFullNameQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;

/**
 * Service interface for handling profile-related queries.
 */
public interface ProfileQueryService {

  /**
   * Handle the query to get all profiles.
   *
   * @param query the query to get all profiles
   * @return a list of all profiles
   */
  List<Profile> handle(GetAllProfilesQuery query);

  /**
   * Handle the query to get a profile by its ID.
   *
   * @param query the query containing the profile ID
   * @return an optional profile matching the ID
   */
  Optional<Profile> handle(GetProfileByIdQuery query);

  /**
   * Handle the query to get a profile by full name.
   *
   * @param query the query containing the full name criteria
   * @return an optional profile matching the full name criteria
   */
  Optional<Profile> handle(GetProfileByFullNameQuery query);

  /**
   * Handle the query to get profiles by age.
   *
   * @param query the query containing the age criteria
   * @return a list of profiles matching the age criteria
   */
  List<Profile> handle(GetProfileByAgeQuery query);
}
