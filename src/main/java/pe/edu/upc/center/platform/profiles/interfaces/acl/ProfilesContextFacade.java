package pe.edu.upc.center.platform.profiles.interfaces.acl;

import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByFullNameQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;

/**
 * Facade for managing profiles, providing methods to create, update, delete, and fetch profiles.
 */
@Service
public class ProfilesContextFacade {
  private final ProfileCommandService profileCommandService;
  private final ProfileQueryService profileQueryService;

  /**
   * Constructs a ProfilesContextFacade with the specified command and query services.
   *
   * @param profileCommandService the service for handling profile commands
   * @param profileQueryService   the service for handling profile queries
   */
  public ProfilesContextFacade(ProfileCommandService profileCommandService,
                               ProfileQueryService profileQueryService) {
    this.profileCommandService = profileCommandService;
    this.profileQueryService = profileQueryService;
  }

  /**
   * Fetches a profile by its ID.
   *
   * @param profileId the ID of the profile to fetch
   * @return an Optional containing the ProfileResource if found, otherwise an empty Optional
   */
  public Optional<ProfileResource> fetchProfileById(Long profileId) {
    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = profileQueryService.handle(getProfileByIdQuery);
    if (optionalProfile.isEmpty()) {
      return Optional.empty();
    }
    var profileResource
        = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return Optional.of(profileResource);
  }

  /**
   * Fetches the profile ID by full name.
   *
   * @param fullName the full name of the profile
   * @return the profile ID if found, otherwise returns 0L
   */
  public Long fetchProfileIdByFullName(String fullName) {
    var getProfileByFullNameQuery = new GetProfileByFullNameQuery(fullName);
    var optionalProfile = profileQueryService.handle(getProfileByFullNameQuery);
    if (optionalProfile.isEmpty()) {
      return 0L;
    }
    return optionalProfile.get().getId();
  }

  /**
   * Checks if a profile with the given full name exists, excluding the profile with
   *     the specified ID.
   *
   * @param fullName the full name to check for existence
   * @param id       the ID of the profile to exclude from the check
   * @return true if a profile with the given full name exists and has a different ID,
   *     false otherwise
   */
  public boolean existsProfileByFullNameAndIdIsNot(String fullName, Long id) {
    var getProfileByFullNameQuery = new GetProfileByFullNameQuery(fullName);
    var optionalProfile = profileQueryService.handle(getProfileByFullNameQuery);
    if (optionalProfile.isEmpty()) {
      return false;
    }
    return optionalProfile.get().getId() != id;
  }

  /**
   * Creates a new profile.
   *
   * @param fullName the full name of the profile
   * @param age      the age of the profile
   * @param street   the street address of the profile
   * @return the ID of the created profile, or 0L if the creation failed
   */
  public Long createProfile(String fullName, int age, String street) {
    var createProfileCommand = new CreateProfileCommand(fullName, age, street);
    var profileId = profileCommandService.handle(createProfileCommand);
    if (Objects.isNull(profileId)) {
      return 0L;
    }
    return profileId;
  }

  /**
   * Updates an existing profile.
   *
   * @param profileId the ID of the profile to update
   * @param fullName  the new full name of the profile
   * @param age       the new age of the profile
   * @param street    the new street address of the profile
   * @return the ID of the updated profile, or 0L if the update failed
   */
  public Long updateProfile(Long profileId, String fullName, int age, String street) {
    var updateProfileCommand = new UpdateProfileCommand(profileId, fullName, age, street);
    var optionalProfile = profileCommandService.handle(updateProfileCommand);
    if (optionalProfile.isEmpty()) {
      return 0L;
    }
    return optionalProfile.get().getId();
  }

  /**
   * Deletes a profile by its ID.
   *
   * @param profileId the ID of the profile to delete
   */
  public void deleteProfile(Long profileId) {
    var deleteProfileCommand = new DeleteProfileCommand(profileId);
    profileCommandService.handle(deleteProfileCommand);
  }
}
