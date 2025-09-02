package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllCourseAssignsQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetCourseAssignByIdQuery;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseAssignRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseAssignQueryServiceImplTest {

    @Mock
    private CourseAssignRepository courseAssignRepository;

    @InjectMocks
    private CourseAssignQueryServiceImpl service;

    @Test
    @DisplayName("handle(GetCourseAssignByIdQuery) should return Optional from repository (AAA)")
    void handle_GetById_ShouldReturnOptional() {
        // Arrange
        var query = new GetCourseAssignByIdQuery(10L);
        CourseAssign expected = mock(CourseAssign.class);
        when(courseAssignRepository.findById(10L)).thenReturn(Optional.of(expected));

        // Act
        Optional<CourseAssign> result = service.handle(query);

        // Assert
        assertTrue(result.isPresent());
        assertSame(expected, result.get());
        verify(courseAssignRepository, times(1)).findById(10L);
        verifyNoMoreInteractions(courseAssignRepository);
    }

    @Test
    @DisplayName("handle(GetAllCourseAssignsQuery) should return List from repository (AAA)")
    void handle_GetAll_ShouldReturnList() {
        // Arrange
        var query = new GetAllCourseAssignsQuery();
        CourseAssign a = mock(CourseAssign.class);
        CourseAssign b = mock(CourseAssign.class);
        when(courseAssignRepository.findAll()).thenReturn(List.of(a, b));

        // Act
        var result = service.handle(query);

        // Assert
        assertEquals(2, result.size());
        assertSame(a, result.get(0));
        assertSame(b, result.get(1));
        verify(courseAssignRepository, times(1)).findAll();
        verifyNoMoreInteractions(courseAssignRepository);
    }
}
