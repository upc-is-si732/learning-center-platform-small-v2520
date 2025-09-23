package pe.edu.upc.center.platform.learning.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.EnrollmentResource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResource should map Enrollment aggregate to EnrollmentResource with items (AAA)")
  void toResource_ShouldMapEnrollment() {
    // Arrange
    Enrollment enrollment = mock(Enrollment.class);
    String studentCode = UUID.randomUUID().toString();
    when(enrollment.getId()).thenReturn(77L);

    when(enrollment.getStudentCode()).thenReturn(new StudentCode(studentCode));
    when(enrollment.getPeriod()).thenReturn("202520");

    EnrollmentStatus status = mock(EnrollmentStatus.class);
    when(status.getStringName()).thenReturn("REGULAR");
    when(enrollment.getStatus()).thenReturn(status);

    // Mock nested course enrolls
    var item1 = mock(CourseEnrollItem.class);
    var item2 = mock(CourseEnrollItem.class);
    // For CourseEnrollResourceFromEntityAssembler, we need getId(), getCourseAssign().getId(), getNumberOfTimes()
    var courseAssign1 = mock(pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign.class);
    var courseAssign2 = mock(pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign.class);
    when(courseAssign1.getId()).thenReturn(10L);
    when(courseAssign2.getId()).thenReturn(20L);
    when(item1.getId()).thenReturn(1L);
    when(item1.getCourseAssign()).thenReturn(courseAssign1);
    when(item1.getNumberOfTimes()).thenReturn(1);
    when(item2.getId()).thenReturn(2L);
    when(item2.getCourseAssign()).thenReturn(courseAssign2);
    when(item2.getNumberOfTimes()).thenReturn(2);

    var courseEnroll = mock(pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseEnroll.class);
    when(courseEnroll.getCourseEnrollItems()).thenReturn(List.of(item1, item2));
    when(enrollment.getCourseEnroll()).thenReturn(courseEnroll);

    // Act
    EnrollmentResource resource = EnrollmentResourceFromEntityAssembler.toResource(enrollment);

    // Assert
    assertNotNull(resource);
    assertEquals(77L, resource.id());
    assertEquals(studentCode, resource.studentCode());
    assertEquals("202520", resource.period());
    assertEquals("REGULAR", resource.status());

    assertEquals(2, resource.courseEnrolls().size());
    assertEquals(1L, resource.courseEnrolls().get(0).id());
    assertEquals(10L, resource.courseEnrolls().get(0).courseAssignId());
    assertEquals(1, resource.courseEnrolls().get(0).numberOfTimes());
    assertEquals(2L, resource.courseEnrolls().get(1).id());
    assertEquals(20L, resource.courseEnrolls().get(1).courseAssignId());
    assertEquals(2, resource.courseEnrolls().get(1).numberOfTimes());
  }
}
