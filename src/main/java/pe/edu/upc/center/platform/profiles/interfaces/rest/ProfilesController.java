package pe.edu.upc.center.platform.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByAgeQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import pe.edu.upc.center.platform.profiles.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;

/**
 * REST controller for managing profiles.
 */
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET,
    RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {

  private final ProfileQueryService profileQueryService;
  private final ProfileCommandService profileCommandService;

  /**
   * Constructor for ProfilesController.
   *
   * @param profileQueryService   the service for handling profile queries
   * @param profileCommandService the service for handling profile commands
   */
  public ProfilesController(ProfileQueryService profileQueryService,
                            ProfileCommandService profileCommandService) {
    this.profileQueryService = profileQueryService;
    this.profileCommandService = profileCommandService;
  }

  /**
   * Endpoint to create a new profile.
   *
   * @param resource the profile data to be created
   * @return a ResponseEntity containing the created profile resource or a bad request status
   *     if creation fails
   */
  @PostMapping
  public ResponseEntity<ProfileResource> createProfile(
      @RequestBody CreateProfileResource resource) {

    var createProfileCommand = CreateProfileCommandFromResourceAssembler
        .toCommandFromResource(resource);
    var profileId = this.profileCommandService.handle(createProfileCommand);

    if (profileId.equals(0L)) {
      return ResponseEntity.badRequest().build();
    }

    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = this.profileQueryService.handle(getProfileByIdQuery);

    var profileResource
        = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
  }

  /**
   * Endpoint to retrieve all profiles or filter by age.
   *
   * @param age optional age parameter to filter profiles
   * @return a ResponseEntity containing a list of profile resources
   */
  @GetMapping
  public ResponseEntity<List<ProfileResource>> getAllProfiles(
      @RequestParam(required = false) Integer age) {

    if (Objects.nonNull(age)) {
      var query = new GetProfileByAgeQuery(age);
      var profiles = this.profileQueryService.handle(query);

      var profileResources = profiles.stream()
          .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
          .collect(Collectors.toList());
      return ResponseEntity.ok(profileResources);
    } else {
      var getAllProfilesQuery = new GetAllProfilesQuery();
      var profiles = this.profileQueryService.handle(getAllProfilesQuery);
      var profileResources = profiles.stream()
          .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
          .collect(Collectors.toList());
      return ResponseEntity.ok(profileResources);
    }

  }

  /**
   * Endpoint to retrieve a profile by its ID.
   *
   * @param profileId the ID of the profile to be retrieved
   * @return a ResponseEntity containing the profile resource or a bad request status if not found
   */
  @GetMapping("/{profileId}")
  public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = this.profileQueryService.handle(getProfileByIdQuery);
    if (optionalProfile.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var profileResource
        = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return ResponseEntity.ok(profileResource);
  }

  /**
   * Endpoint to update an existing profile.
   *
   * @param profileId the ID of the profile to be updated
   * @param resource  the updated profile data
   * @return a ResponseEntity containing the updated profile resource or a bad request status
   *     if the update fails
   */
  @PutMapping("/{profileId}")
  public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long profileId,
                                                       @RequestBody ProfileResource resource) {
    var updateProfileCommand
        = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
    var optionalProfile = this.profileCommandService.handle(updateProfileCommand);

    if (optionalProfile.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var profileResource
        = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return ResponseEntity.ok(profileResource);
  }

  /**
   * Endpoint to delete a profile by its ID.
   *
   * @param profileId the ID of the profile to be deleted
   * @return a ResponseEntity with no content if deletion is successful
   */
  @DeleteMapping("/{profileId}")
  public ResponseEntity<?> deleteProfile(@PathVariable Long profileId) {
    var deleteProfileCommand = new DeleteProfileCommand(profileId);
    this.profileCommandService.handle(deleteProfileCommand);
    return ResponseEntity.noContent().build();
  }

  /**
   * Endpoint to search profiles by age.
   *
   * @param age the age to filter profiles (optional)
   * @return a list of profiles matching the specified age
   */
  @GetMapping("/search")
  public ResponseEntity<List<ProfileResource>> getProfileByAge(
      @RequestParam(required = false) Integer age) {
    var query = new GetProfileByAgeQuery(age);
    var profiles = this.profileQueryService.handle(query);

    var profileResources = profiles.stream()
        .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());
    return ResponseEntity.ok(profileResources);
  }
}
