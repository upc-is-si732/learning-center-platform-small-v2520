package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;

import static org.junit.jupiter.api.Assertions.*;

class CreateCourseEnrollCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommand should map CreateCourseEnrollResource to CreateCourseEnrollCommand (AAA)")
  void toCommand_ShouldMap() {
    // Arrange
    var resource = new CreateCourseEnrollResource(10L, 3);

    // Act
    CreateCourseEnrollCommand cmd = CreateCourseEnrollCommandFromResourceAssembler.toCommand(resource);

    // Assert
    assertNotNull(cmd);
    assertEquals(10L, cmd.courseAssignId());
    assertEquals(3, cmd.numberOfTimes());
  }
}
