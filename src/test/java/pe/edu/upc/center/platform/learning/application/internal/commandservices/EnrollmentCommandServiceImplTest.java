package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.CourseAssign;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Enrollment;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollItemCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateEnrollmentCommand;
import pe.edu.upc.center.platform.learning.domain.model.entities.CourseEnrollItem;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseAssignRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseEnrollItemRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentStatusRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentCommandServiceImplTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private CourseAssignRepository courseAssignRepository;
    @Mock private EnrollmentStatusRepository enrollmentStatusRepository;
    @Mock private CourseEnrollItemRepository courseEnrollItemRepository;

    @InjectMocks private EnrollmentCommandServiceImpl service;

    @Test
    @DisplayName("handle(CreateEnrollmentCommand) should create enrollment with items (AAA)")
    void handle_CreateEnrollment_ShouldCreate() {
        // Arrange
        var student = new StudentCode("STU001");
        var courseEnrolls = List.of(
                new pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollCommand(1L, 1)
        );
        var command = new CreateEnrollmentCommand(student, "2025-1", "CREATED", courseEnrolls);

        when(enrollmentRepository.existsByStudentCodeAndAndPeriod(student, "2025-1")).thenReturn(false);
        var status = mock(EnrollmentStatus.class);
        when(enrollmentStatusRepository.findByName(EnrollmentStatuses.valueOf("CREATED"))).thenReturn(Optional.of(status));
        var courseAssign = mock(CourseAssign.class);
        when(courseAssignRepository.findById(1L)).thenReturn(Optional.of(courseAssign));
        // save just returns same entity
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Long id = service.handle(command);

        // Assert
        assertNull(id, "As repositories are mocked, id is expected to be null unless test sets it");
        verify(enrollmentRepository).existsByStudentCodeAndAndPeriod(student, "2025-1");
        verify(enrollmentStatusRepository).findByName(EnrollmentStatuses.valueOf("CREATED"));
        verify(courseAssignRepository).findById(1L);
        verify(enrollmentRepository).save(any(Enrollment.class));
        verifyNoMoreInteractions(enrollmentRepository, enrollmentStatusRepository, courseAssignRepository, courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(CreateEnrollmentCommand) should throw when duplicate enrollment (AAA)")
    void handle_CreateEnrollment_ShouldThrow_WhenDuplicate() {
        // Arrange
        var student = new StudentCode("STU001");
        var command = new CreateEnrollmentCommand(student, "2025-1", "CREATED", List.of());
        when(enrollmentRepository.existsByStudentCodeAndAndPeriod(student, "2025-1")).thenReturn(true);

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("already exists"));
        verify(enrollmentRepository).existsByStudentCodeAndAndPeriod(student, "2025-1");
        verifyNoMoreInteractions(enrollmentRepository);
        verifyNoInteractions(enrollmentStatusRepository, courseAssignRepository, courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(CreateEnrollmentCommand) should throw when status not found (AAA)")
    void handle_CreateEnrollment_ShouldThrow_WhenStatusNotFound() {
        // Arrange
        var student = new StudentCode("STU001");
        var command = new CreateEnrollmentCommand(student, "2025-1", "CREATED", List.of());
        when(enrollmentRepository.existsByStudentCodeAndAndPeriod(student, "2025-1")).thenReturn(false);
        when(enrollmentStatusRepository.findByName(EnrollmentStatuses.valueOf("CREATED"))).thenReturn(Optional.empty());

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("status"));
        verify(enrollmentRepository).existsByStudentCodeAndAndPeriod(student, "2025-1");
        verify(enrollmentStatusRepository).findByName(EnrollmentStatuses.valueOf("CREATED"));
        verifyNoMoreInteractions(enrollmentRepository, enrollmentStatusRepository);
        verifyNoInteractions(courseAssignRepository, courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(CreateEnrollmentCommand) should throw when some courseAssign not found (AAA)")
    void handle_CreateEnrollment_ShouldThrow_WhenCourseAssignMissing() {
        // Arrange
        var student = new StudentCode("STU001");
        var courseEnrolls = List.of(new pe.edu.upc.center.platform.learning.domain.model.commands.CreateCourseEnrollCommand(99L, 1));
        var command = new CreateEnrollmentCommand(student, "2025-1", "CREATED", courseEnrolls);
        when(enrollmentRepository.existsByStudentCodeAndAndPeriod(student, "2025-1")).thenReturn(false);
        var status = mock(EnrollmentStatus.class);
        when(enrollmentStatusRepository.findByName(EnrollmentStatuses.valueOf("CREATED"))).thenReturn(Optional.of(status));
        when(courseAssignRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        verify(enrollmentRepository).existsByStudentCodeAndAndPeriod(student, "2025-1");
        verify(enrollmentStatusRepository).findByName(EnrollmentStatuses.valueOf("CREATED"));
        verify(courseAssignRepository).findById(99L);
        verifyNoInteractions(courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(CreateEnrollmentCommand) should wrap repository exception (AAA)")
    void handle_CreateEnrollment_ShouldWrap_WhenRepoThrows() {
        // Arrange
        var student = new StudentCode("STU001");
        var command = new CreateEnrollmentCommand(student, "2025-1", "CREATED", List.of());
        when(enrollmentRepository.existsByStudentCodeAndAndPeriod(student, "2025-1")).thenReturn(false);
        var status = mock(EnrollmentStatus.class);
        when(enrollmentStatusRepository.findByName(EnrollmentStatuses.valueOf("CREATED"))).thenReturn(Optional.of(status));
        when(enrollmentRepository.save(any(Enrollment.class))).thenThrow(new RuntimeException("db down"));

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Error while saving enrollment"));
        verify(enrollmentRepository).existsByStudentCodeAndAndPeriod(student, "2025-1");
        verify(enrollmentStatusRepository).findByName(EnrollmentStatuses.valueOf("CREATED"));
        verify(enrollmentRepository).save(any(Enrollment.class));
        verifyNoInteractions(courseAssignRepository, courseEnrollItemRepository);
    }

    @Test
    @DisplayName("handle(CreateCourseEnrollItemCommand) should create item (AAA)")
    void handle_CreateCourseEnrollItem_ShouldCreate() {
        // Arrange
        var command = new CreateCourseEnrollItemCommand(10L, 20L, 2);
        var enrollment = mock(Enrollment.class);
        var courseAssign = mock(CourseAssign.class);
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.of(enrollment));
        when(courseAssignRepository.findById(20L)).thenReturn(Optional.of(courseAssign));
        when(courseEnrollItemRepository.existsByEnrollmentAndCourseAssign(enrollment, courseAssign)).thenReturn(false);
        when(courseEnrollItemRepository.save(any(CourseEnrollItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Long id = service.handle(command);

        // Assert
        assertNull(id);
        verify(enrollmentRepository).findById(10L);
        verify(courseAssignRepository).findById(20L);
        verify(courseEnrollItemRepository).existsByEnrollmentAndCourseAssign(enrollment, courseAssign);
        verify(courseEnrollItemRepository).save(any(CourseEnrollItem.class));
        verifyNoMoreInteractions(enrollmentRepository, courseAssignRepository, courseEnrollItemRepository);
        verifyNoInteractions(enrollmentStatusRepository);
    }

    @Test
    @DisplayName("handle(CreateCourseEnrollItemCommand) should throw when enrollment missing (AAA)")
    void handle_CreateCourseEnrollItem_ShouldThrow_WhenEnrollmentMissing() {
        // Arrange
        var command = new CreateCourseEnrollItemCommand(10L, 20L, 2);
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        verify(enrollmentRepository).findById(10L);
        verifyNoInteractions(courseAssignRepository, courseEnrollItemRepository, enrollmentStatusRepository);
    }

    @Test
    @DisplayName("handle(CreateCourseEnrollItemCommand) should throw when courseAssign missing (AAA)")
    void handle_CreateCourseEnrollItem_ShouldThrow_WhenCourseAssignMissing() {
        // Arrange
        var command = new CreateCourseEnrollItemCommand(10L, 20L, 2);
        var enrollment = mock(Enrollment.class);
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.of(enrollment));
        when(courseAssignRepository.findById(20L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        verify(enrollmentRepository).findById(10L);
        verify(courseAssignRepository).findById(20L);
        verifyNoInteractions(courseEnrollItemRepository, enrollmentStatusRepository);
    }

    @Test
    @DisplayName("handle(CreateCourseEnrollItemCommand) should throw when duplicate item (AAA)")
    void handle_CreateCourseEnrollItem_ShouldThrow_WhenDuplicateItem() {
        // Arrange
        var command = new CreateCourseEnrollItemCommand(10L, 20L, 2);
        var enrollment = mock(Enrollment.class);
        var courseAssign = mock(CourseAssign.class);
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.of(enrollment));
        when(courseAssignRepository.findById(20L)).thenReturn(Optional.of(courseAssign));
        when(courseEnrollItemRepository.existsByEnrollmentAndCourseAssign(enrollment, courseAssign)).thenReturn(true);

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("already exists"));
        verify(enrollmentRepository).findById(10L);
        verify(courseAssignRepository).findById(20L);
        verify(courseEnrollItemRepository).existsByEnrollmentAndCourseAssign(enrollment, courseAssign);
        verifyNoMoreInteractions(enrollmentRepository, courseAssignRepository, courseEnrollItemRepository);
        verifyNoInteractions(enrollmentStatusRepository);
    }

    @Test
    @DisplayName("handle(CreateCourseEnrollItemCommand) should wrap repository exception (AAA)")
    void handle_CreateCourseEnrollItem_ShouldWrap_WhenRepoThrows() {
        // Arrange
        var command = new CreateCourseEnrollItemCommand(10L, 20L, 2);
        var enrollment = mock(Enrollment.class);
        var courseAssign = mock(CourseAssign.class);
        when(enrollmentRepository.findById(10L)).thenReturn(Optional.of(enrollment));
        when(courseAssignRepository.findById(20L)).thenReturn(Optional.of(courseAssign));
        when(courseEnrollItemRepository.existsByEnrollmentAndCourseAssign(enrollment, courseAssign)).thenReturn(false);
        when(courseEnrollItemRepository.save(any(CourseEnrollItem.class))).thenThrow(new RuntimeException("db down"));

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Error while saving course enroll item"));
        verify(enrollmentRepository).findById(10L);
        verify(courseAssignRepository).findById(20L);
        verify(courseEnrollItemRepository).existsByEnrollmentAndCourseAssign(enrollment, courseAssign);
        verify(courseEnrollItemRepository).save(any(CourseEnrollItem.class));
        verifyNoMoreInteractions(enrollmentRepository, courseAssignRepository, courseEnrollItemRepository);
        verifyNoInteractions(enrollmentStatusRepository);
    }
}
