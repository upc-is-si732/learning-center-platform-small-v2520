package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.CreateProfileResource;

import static org.junit.jupiter.api.Assertions.*;

class CreateProfileCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource should map CreateProfileResource to CreateProfileCommand (AAA)")
  void toCommandFromResource_ShouldMap() {
    // Arrange
    var resource = new CreateProfileResource("John Doe", 21, "Main St");

    // Act
    CreateProfileCommand cmd = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);

    // Assert
    assertNotNull(cmd);
    assertEquals("John Doe", cmd.fullName());
    assertEquals(21, cmd.age());
    assertEquals("Main St", cmd.street());
  }
}
