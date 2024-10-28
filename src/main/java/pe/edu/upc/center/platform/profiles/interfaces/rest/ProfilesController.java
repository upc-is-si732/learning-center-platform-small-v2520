package pe.edu.upc.center.platform.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {

  private final ProfileQueryService profileQueryService;
  private final ProfileCommandService profileCommandService;

  public ProfilesController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
    this.profileQueryService = profileQueryService;
    this.profileCommandService = profileCommandService;
  }

  @PostMapping
  public ResponseEntity<ProfileResource> createProfile( @RequestBody CreateProfileResource resource) {

    var createProfileCommand = CreateProfileCommandFromResourceAssembler
        .toCommandFromResource(resource);
    var profileId = this.profileCommandService.handle(createProfileCommand);

    if (profileId.equals(0L)) {
      return ResponseEntity.badRequest().build();
    }

    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = this.profileQueryService.handle(getProfileByIdQuery);

    var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ProfileResource>> getAllProfiles(@RequestParam(required = false) Integer age) {

    if(age != null) {
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

  @GetMapping("/{profileId}")
  public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
    var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
    var optionalProfile = this.profileQueryService.handle(getProfileByIdQuery);
    if (optionalProfile.isEmpty())
      return ResponseEntity.badRequest().build();
    var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return ResponseEntity.ok(profileResource);
  }

  @PutMapping("/{profileId}")
  public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long profileId, @RequestBody ProfileResource resource) {
    var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(profileId, resource);
    var optionalProfile = this.profileCommandService.handle(updateProfileCommand);

    if (optionalProfile.isEmpty())
      return ResponseEntity.badRequest().build();
    var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(optionalProfile.get());
    return ResponseEntity.ok(profileResource);
  }

  @DeleteMapping("/{profileId}")
  public ResponseEntity<?> deleteProfile(@PathVariable Long profileId) {
    var deleteProfileCommand = new DeleteProfileCommand(profileId);
    this.profileCommandService.handle(deleteProfileCommand);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<ProfileResource>> getProfileByAge(@RequestParam(required = false) Integer age) {
    var query = new GetProfileByAgeQuery(age);
    var profiles = this.profileQueryService.handle(query);

    var profileResources = profiles.stream()
        .map(ProfileResourceFromEntityAssembler::toResourceFromEntity)
        .collect(Collectors.toList());
    return ResponseEntity.ok(profileResources);
  }
}
