package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CreateCourseAssignResource;

import static org.junit.jupiter.api.Assertions.*;

class CreateCourseAssignCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommand should map CreateCourseAssignResource to CreateCourseAssignCommand (AAA)")
  void toCommand_ShouldMap() {
    // Arrange
    var resource = new CreateCourseAssignResource("SI732", "AB01", 9742L, "SB708");

    // Act
    CreateCourseAssignCommand cmd = CreateCourseAssignCommandFromResourceAssembler.toCommand(resource);

    // Assert
    assertNotNull(cmd);
    assertEquals("SI732", cmd.courseId());
    assertEquals("AB01", cmd.section());
    assertEquals(9742L, cmd.professorId());
    assertEquals("SB708", cmd.classroomId());
  }
}
