package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseEnrollItemByIdQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseEnrollItemRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentQueryServiceImplTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private CourseEnrollItemRepository courseEnrollItemRepository;

    @InjectMocks private EnrollmentQueryServiceImpl service;

    @Test
    @DisplayName("handle(GetEnrollmentByIdQuery) should return Optional from repository (AAA)")
    void handle_GetEnrollmentById_ShouldReturnOptional() {
        // Arrange
        var query = new GetEnrollmentByIdQuery(7L);
        Enrollment expected = mock(Enrollment.class);
        when(enrollmentRepository.findById(7L)).thenReturn(Optional.of(expected));

        // Act
        Optional<Enrollment> result = service.handle(query);

        // Assert
        assertTrue(result.isPresent());
        assertSame(expected, result.get());
        verify(enrollmentRepository, times(1)).findById(7L);
        verifyNoMoreInteractions(enrollmentRepository);
        verifyNoInteractions(courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(GetAllEnrollmentsQuery) should return list from repository (AAA)")
    void handle_GetAllEnrollments_ShouldReturnList() {
        // Arrange
        var query = new GetAllEnrollmentsQuery();
        Enrollment a = mock(Enrollment.class);
        Enrollment b = mock(Enrollment.class);
        when(enrollmentRepository.findAll()).thenReturn(List.of(a, b));

        // Act
        List<Enrollment> result = service.handle(query);

        // Assert
        assertEquals(2, result.size());
        assertSame(a, result.get(0));
        assertSame(b, result.get(1));
        verify(enrollmentRepository, times(1)).findAll();
        verifyNoMoreInteractions(enrollmentRepository);
        verifyNoInteractions(courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(GetCourseEnrollItemByIdQuery) should return Optional from repository (AAA)")
    void handle_GetCourseEnrollItemById_ShouldReturnOptional() {
        // Arrange
        var query = new GetCourseEnrollItemByIdQuery(15L);
        CourseEnrollItem expected = mock(CourseEnrollItem.class);
        when(courseEnrollItemRepository.findById(15L)).thenReturn(Optional.of(expected));

        // Act
        Optional<CourseEnrollItem> result = service.handle(query);

        // Assert
        assertTrue(result.isPresent());
        assertSame(expected, result.get());
        verify(courseEnrollItemRepository, times(1)).findById(15L);
        verifyNoMoreInteractions(courseEnrollItemRepository);
        verifyNoInteractions(enrollmentRepository);
    }
}
