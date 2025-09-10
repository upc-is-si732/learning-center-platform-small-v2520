package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.commands.UpdateStudentCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.StudentResource;

import static org.junit.jupiter.api.Assertions.*;

class UpdateStudentCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource should map studentCode + StudentResource to UpdateStudentCommand (AAA)")
  void toCommandFromResource_ShouldMap() {
    // Arrange
    var resource = new StudentResource("ignored", "Jane Doe", 21, "Main St", 5L, "2025-1");

    // Act
    UpdateStudentCommand cmd = UpdateStudentCommandFromResourceAssembler.toCommandFromResource("STU-001", resource);

    // Assert
    assertNotNull(cmd);
    assertEquals("STU-001", cmd.studentCode().studentCode());
    assertEquals("Jane Doe", cmd.fullName());
    assertEquals(21, cmd.age());
    assertEquals("Main St", cmd.street());
  }
}
