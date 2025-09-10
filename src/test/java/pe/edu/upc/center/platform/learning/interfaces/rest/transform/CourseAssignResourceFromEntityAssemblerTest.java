package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseAssignResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseAssignResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResource should map CourseAssign entity to CourseAssignResource (AAA)")
  void toResource_ShouldMapEntityToResource() {
    // Arrange
    CourseAssign entity = mock(CourseAssign.class);
    when(entity.getId()).thenReturn(5L);
    when(entity.getCourseId()).thenReturn("SI732");
    when(entity.getSection()).thenReturn("AB01");
    when(entity.getProfessorId()).thenReturn(999L);
    when(entity.getClassroomId()).thenReturn("SB708");

    // Act
    CourseAssignResource resource = CourseAssignResourceFromEntityAssembler.toResource(entity);

    // Assert
    assertNotNull(resource);
    assertEquals(5L, resource.id());
    assertEquals("SI732", resource.courseId());
    assertEquals("AB01", resource.section());
    assertEquals(999L, resource.professorId());
    assertEquals("SB708", resource.classroomId());
    verify(entity).getId();
    verify(entity).getCourseId();
    verify(entity).getSection();
    verify(entity).getProfessorId();
    verify(entity).getClassroomId();
    verifyNoMoreInteractions(entity);
  }
}
