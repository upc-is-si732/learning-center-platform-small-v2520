package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateStudentResource;

import static org.junit.jupiter.api.Assertions.*;

class CreateStudentCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource should map CreateStudentResource to CreateStudentCommand (AAA)")
  void toCommandFromResource_ShouldMap() {
    // Arrange
    var resource = new CreateStudentResource("John Doe", 20, "Main St", 9L, "2025-1");

    // Act
    CreateStudentCommand cmd = CreateStudentCommandFromResourceAssembler.toCommandFromResource(resource);

    // Assert
    assertNotNull(cmd);
    assertEquals("John Doe", cmd.fullName());
    assertEquals(20, cmd.age());
    assertEquals("Main St", cmd.street());
    assertEquals(9L, cmd.programId());
    assertEquals("2025-1", cmd.startPeriod());
  }
}
