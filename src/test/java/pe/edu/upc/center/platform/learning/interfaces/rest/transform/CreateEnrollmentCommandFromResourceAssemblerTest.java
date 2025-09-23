package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseEnrollResource;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateEnrollmentResource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateEnrollmentCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommand should map CreateEnrollmentResource to CreateEnrollmentCommand (AAA)")
  void toCommand_ShouldMapResourceToCommand() {
    // Arrange
    var courseEnrolls = List.of(
        new CreateCourseEnrollResource(10L, 1),
        new CreateCourseEnrollResource(20L, 2)
    );
    var studentCode = UUID.randomUUID().toString();
    var resource = new CreateEnrollmentResource(studentCode, "202520", "REGULAR", courseEnrolls);

    // Act
    CreateEnrollmentCommand cmd = CreateEnrollmentCommandFromResourceAssembler.toCommand(resource);

    // Assert
    assertNotNull(cmd);
    assertEquals(studentCode, cmd.studentCode().studentCode());
    assertEquals("202520", cmd.period());
    assertEquals("REGULAR", cmd.status());

    List<CreateCourseEnrollCommand> items = cmd.courseEnrolls();
    assertEquals(2, items.size());
    assertEquals(10L, items.get(0).courseAssignId());
    assertEquals(1, items.get(0).numberOfTimes());
    assertEquals(20L, items.get(1).courseAssignId());
    assertEquals(2, items.get(1).numberOfTimes());
  }
}
