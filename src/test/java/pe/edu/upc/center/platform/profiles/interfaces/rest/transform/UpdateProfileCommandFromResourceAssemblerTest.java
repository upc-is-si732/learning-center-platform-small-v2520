package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProfileCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource should map ProfileResource + id to UpdateProfileCommand (AAA)")
  void toCommandFromResource_ShouldMap() {
    // Arrange
    Long id = 55L;
    var resource = new ProfileResource(null, "Alice", 30, "Pine Rd");

    // Act
    UpdateProfileCommand cmd = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(id, resource);

    // Assert
    assertNotNull(cmd);
    assertEquals(55L, cmd.profileId());
    assertEquals("Alice", cmd.fullName());
    assertEquals(30, cmd.age());
    assertEquals("Pine Rd", cmd.street());
  }
}
