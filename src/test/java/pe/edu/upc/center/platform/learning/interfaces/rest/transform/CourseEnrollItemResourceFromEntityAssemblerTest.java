package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollItemResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseEnrollItemResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResource should map CourseEnrollItem to CourseEnrollItemResource (AAA)")
  void toResource_ShouldMap() {
    // Arrange
    CourseEnrollItem entity = mock(CourseEnrollItem.class);
    when(entity.getId()).thenReturn(100L);
    Enrollment enrollment = mock(Enrollment.class);
    when(enrollment.getId()).thenReturn(10L);
    when(entity.getEnrollment()).thenReturn(enrollment);
    CourseAssign courseAssign = mock(CourseAssign.class);
    when(courseAssign.getId()).thenReturn(20L);
    when(entity.getCourseAssign()).thenReturn(courseAssign);
    when(entity.getNumberOfTimes()).thenReturn(3);

    // Act
    CourseEnrollItemResource resource = CourseEnrollItemResourceFromEntityAssembler.toResource(entity);

    // Assert
    assertNotNull(resource);
    assertEquals(100L, resource.id());
    assertEquals(10L, resource.enrollmentId());
    assertEquals(20L, resource.courseAssignId());
    assertEquals(3, resource.numberOfTimes());

    verify(entity).getId();
    verify(entity).getEnrollment();
    verify(enrollment).getId();
    verify(entity).getCourseAssign();
    verify(courseAssign).getId();
    verify(entity).getNumberOfTimes();
    verifyNoMoreInteractions(entity, enrollment, courseAssign);
  }
}
