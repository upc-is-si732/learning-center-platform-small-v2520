package pe.edu.upc.center.platform.profiles.interfaces.acl;

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

import java.util.Optional;

@Service
public class ProfilesContextFacade {
  private final ProfileCommandService profileCommandService;
  private final ProfileQueryService profileQueryService;

  public ProfilesContextFacade(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
    this.profileCommandService = profileCommandService;
    this.profileQueryService = profileQueryService;
  }

  public Optional<ProfileResource> fetchProfileById(Long profileId) {
    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = profileQueryService.handle(getProfileByIdQuery);
    if (optionalProfile.isEmpty()) {
      return Optional.empty();
    }
    var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return Optional.of(profileResource);
  }

  public Long fetchProfileIdByFullName(String fullName) {
    var getProfileByFullNameQuery = new GetProfileByFullNameQuery(fullName);
    var optionalProfile = profileQueryService.handle(getProfileByFullNameQuery);
    if (optionalProfile.isEmpty()) {
      return 0L;
    }
    return optionalProfile.get().getId();
  }

  public boolean existsProfileByFullNameAndIdIsNot(String fullName, Long id) {
    var getProfileByFullNameQuery = new GetProfileByFullNameQuery(fullName);
    var optionalProfile = profileQueryService.handle(getProfileByFullNameQuery);
    if (optionalProfile.isEmpty()) {
      return false;
    }
    return optionalProfile.get().getId() != id;
  }

  public Long createProfile(String fullName, int age, String street) {
    var CreateProfileCommand = new CreateProfileCommand(fullName, age, street);
    var profileId = profileCommandService.handle(CreateProfileCommand);
    if (profileId.equals(null)) {
      return 0L;
    }
    return profileId;
  }

  public Long updateProfile(Long profileId, String fullName, int age, String street) {
    var updateProfileCommand = new UpdateProfileCommand(profileId, fullName, age, street);
    var optionalProfile = profileCommandService.handle(updateProfileCommand);
    if (optionalProfile.isEmpty()) {
      return 0L;
    }
    return optionalProfile.get().getId();
  }

  public void deleteProfile(Long profileId) {
    var deleteProfileCommand = new DeleteProfileCommand(profileId);
    profileCommandService.handle(deleteProfileCommand);
  }
}
