package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;

import static org.junit.jupiter.api.Assertions.*;

class CreateCourseEnrollItemCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommand should map enrollmentId + CreateCourseEnrollResource to CreateCourseEnrollItemCommand (AAA)")
  void toCommand_ShouldMap() {
    // Arrange
    Long enrollmentId = 77L;
    var resource = new CreateCourseEnrollResource(88L, 2);

    // Act
    CreateCourseEnrollItemCommand cmd = CreateCourseEnrollItemCommandFromResourceAssembler.toCommand(enrollmentId, resource);

    // Assert
    assertNotNull(cmd);
    assertEquals(77L, cmd.enrollmentId());
    assertEquals(88L, cmd.courseAssignId());
    assertEquals(2, cmd.numberOfTimes());
  }
}
