package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseAssignCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.CourseId;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseAssignRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseAssignCommandServiceImplTest {

    @Mock
    private CourseAssignRepository courseAssignRepository;

    @InjectMocks
    private CourseAssignCommandServiceImpl service;

    @Test
    @DisplayName("handle should create a new CourseAssign when it does not exist (AAA)")
    void handle_ShouldCreate_WhenNotExists() {
        // Arrange
        var command = new CreateCourseAssignCommand("INF01", "A01", 5L, "C101");
        when(courseAssignRepository.existsByCourseIdAndSection(new CourseId("INF01"), "A01")).thenReturn(false);
        // Save returns entity, but service doesn't use it; still we can simulate no exception
        when(courseAssignRepository.save(any(CourseAssign.class))).thenAnswer(invocation -> {
            CourseAssign ca = invocation.getArgument(0);
            // simulate JPA generated id by reflection via AuditableAbstractAggregateRoot getter is private; but service returns ca.getId(), which stays null here
            return ca;
        });

        // Act
        Long id = service.handle(command);

        // Assert
        assertNull(id, "Expected id to be null because repository save is mocked and no id is set");
        verify(courseAssignRepository, times(1)).existsByCourseIdAndSection(new CourseId("INF01"), "A01");
        verify(courseAssignRepository, times(1)).save(any(CourseAssign.class));
        verifyNoMoreInteractions(courseAssignRepository);
    }

    @Test
    @DisplayName("handle should throw when CourseAssign already exists (AAA)")
    void handle_ShouldThrow_WhenAlreadyExists() {
        // Arrange
        var command = new CreateCourseAssignCommand("INF01", "A01", 5L, "C101");
        when(courseAssignRepository.existsByCourseIdAndSection(new CourseId("INF01"), "A01"))
                .thenReturn(true);

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("already exists"));
        verify(courseAssignRepository, times(1)).existsByCourseIdAndSection(new CourseId("INF01"), "A01");
        verify(courseAssignRepository, never()).save(any());
        verifyNoMoreInteractions(courseAssignRepository);
    }

    @Test
    @DisplayName("handle should wrap repository exception into IllegalArgumentException (AAA)")
    void handle_ShouldWrap_WhenRepositoryThrows() {
        // Arrange
        var command = new CreateCourseAssignCommand("INF01", "A01", 5L, "C101");
        when(courseAssignRepository.existsByCourseIdAndSection(new CourseId("INF01"), "A01")).thenReturn(false);
        when(courseAssignRepository.save(any(CourseAssign.class))).thenThrow(new RuntimeException("db down"));

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Error while saving course assign"));
        verify(courseAssignRepository, times(1)).existsByCourseIdAndSection(new CourseId("INF01"), "A01");
        verify(courseAssignRepository, times(1)).save(any(CourseAssign.class));
        verifyNoMoreInteractions(courseAssignRepository);
    }
}
