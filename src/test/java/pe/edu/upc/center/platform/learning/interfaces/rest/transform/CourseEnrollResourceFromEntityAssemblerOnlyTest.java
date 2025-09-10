package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.CourseEnrollResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseEnrollResourceFromEntityAssemblerOnlyTest {

  @Test
  @DisplayName("toResource should map CourseEnrollItem to CourseEnrollResource (AAA)")
  void toResource_ShouldMap() {
    // Arrange
    CourseEnrollItem entity = mock(CourseEnrollItem.class);
    when(entity.getId()).thenReturn(1L);
    CourseAssign courseAssign = mock(CourseAssign.class);
    when(courseAssign.getId()).thenReturn(50L);
    when(entity.getCourseAssign()).thenReturn(courseAssign);
    when(entity.getNumberOfTimes()).thenReturn(2);

    // Act
    CourseEnrollResource resource = CourseEnrollResourceFromEntityAssembler.toResource(entity);

    // Assert
    assertNotNull(resource);
    assertEquals(1L, resource.id());
    assertEquals(50L, resource.courseAssignId());
    assertEquals(2, resource.numberOfTimes());

    verify(entity).getId();
    verify(entity).getCourseAssign();
    verify(courseAssign).getId();
    verify(entity).getNumberOfTimes();
    verifyNoMoreInteractions(entity, courseAssign);
  }
}
