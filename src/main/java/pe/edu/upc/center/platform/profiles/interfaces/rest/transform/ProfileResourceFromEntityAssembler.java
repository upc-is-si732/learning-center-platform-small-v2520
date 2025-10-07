package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

/**
 * Assembler class to convert Profile entities to ProfileResource DTOs.
 */
public class ProfileResourceFromEntityAssembler {

  /** Converts a Profile entity to a ProfileResource DTO.
   *
   * @param entity the Profile entity to convert
   * @return the corresponding ProfileResource DTO
   */
  public static ProfileResource toResourceFromEntity(Profile entity) {
    return new ProfileResource(entity.getId(), entity.getFullName(), entity.getAge(),
        entity.getAddress());
  }
}
