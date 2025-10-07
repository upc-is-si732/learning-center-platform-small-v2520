package pe.edu.upc.center.platform.learning.application.internal.outboundservices.acl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.StudentResource;
import pe.edu.upc.center.platform.profiles.interfaces.acl.ProfilesContextFacade;

/**
 * Service class for interacting with external profile services via ProfilesContextFacade.
 */
@Service
public class ExternalProfileService {

  private final ProfilesContextFacade profilesContextFacade;

  /**
   * Constructor for ExternalProfileService.
   *
   * @param profilesContextFacade the ProfilesContextFacade to be used for profile operations
   */
  public ExternalProfileService(ProfilesContextFacade profilesContextFacade) {
    this.profilesContextFacade = profilesContextFacade;
  }

  /**
   * Fetches a ProfileId by full name using the ProfilesContextFacade.
   *
   * @param fullName the full name of the profile to fetch
   * @return an Optional containing the ProfileId if found, or an empty Optional if not found
   */
  public Optional<ProfileId> fetchProfileIdByFullName(String fullName) {
    var profileId = this.profilesContextFacade.fetchProfileIdByFullName(fullName);
    if (profileId.equals(0L)) {
      return Optional.empty();
    }
    return Optional.of(new ProfileId(profileId));
  }

  /**
   * Checks if a profile exists with the given full name and a different ID using
   *     the ProfilesContextFacade.
   *
   * @param fullName the full name to check for existence
   * @param id       the ID to exclude from the check
   * @return true if a profile exists with the given full name and a different ID, false otherwise
   */
  public boolean existsProfileByFullNameAndIdIsNot(String fullName, long id) {
    return this.profilesContextFacade.existsProfileByFullNameAndIdIsNot(fullName, id);
  }

  /**
   * Creates a new profile using the ProfilesContextFacade.
   *
   * @param fullName the full name of the profile
   * @param age      the age of the profile
   * @param street   the street address of the profile
   * @return an Optional containing the created ProfileId if successful, or an empty Optional
   *     if creation failed
   */
  public Optional<ProfileId> createProfile(String fullName, int age, String street) {
    var profileId = this.profilesContextFacade.createProfile(fullName, age, street);
    if (profileId.equals(0L)) {
      return Optional.empty();
    }
    return Optional.of(new ProfileId(profileId));
  }

  /**
   * Updates a profile by its ID using the ProfilesContextFacade.
   *
   * @param profileId the ID of the profile to be updated
   * @param fullName  the new full name for the profile
   * @param age       the new age for the profile
   * @param street    the new street address for the profile
   * @return an Optional containing the updated ProfileId if successful, or an empty Optional
   *     if the update failed
   */
  public Optional<ProfileId> updateProfile(Long profileId, String fullName, int age,
                                           String street) {
    var profileIdUpdated = this.profilesContextFacade.updateProfile(profileId, fullName,
        age, street);
    if (profileIdUpdated.equals(0L)) {
      return Optional.empty();
    }
    return Optional.of(new ProfileId(profileIdUpdated));
  }

  /**
   * Deletes a profile by its ID using the ProfilesContextFacade.
   *
   * @param profileId the ID of the profile to be deleted
   */
  public void deleteProfile(Long profileId) {
    this.profilesContextFacade.deleteProfile(profileId);
  }

  /**
   * Fetches a StudentResource by retrieving profile information using the student's profile ID.
   *
   * @param student the Student entity containing the profile ID
   * @return an Optional containing the StudentResource if the profile is found, or an empty
   *     Optional if not found
   */
  // Evaluate if these method is necessary here or implement it in the transform layer
  public Optional<StudentResource> fetchStudentResourceFromProfileId(Student student) {
    var profileResource = this.profilesContextFacade.fetchProfileById(student.getProfileId());
    if (profileResource.isEmpty()) {
      return Optional.empty();
    }

    var studentResource = new StudentResource(student.getStudentCode().studentCode(),
        profileResource.get().fullName(), profileResource.get().age(),
        profileResource.get().street(), student.getProgramId(), student.getStartPeriod());
    return Optional.of(studentResource);
  }
}
