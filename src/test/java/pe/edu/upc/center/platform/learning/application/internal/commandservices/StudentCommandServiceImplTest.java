package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.application.internal.outboundservices.acl.ExternalProfileService;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.commands.CreateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.DeleteStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.commands.UpdateStudentCommand;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentCommandServiceImplTest {

    @Mock private StudentRepository studentRepository;
    @Mock private ExternalProfileService externalProfileService;

    @InjectMocks private StudentCommandServiceImpl service;

    @Test
    @DisplayName("handle(CreateStudentCommand) should create student when ok (AAA)")
    void handle_Create_ShouldCreate() {
        // Arrange
        var command = new CreateStudentCommand("John Doe", 20, "Main St", 9L, "2025-1");
        when(externalProfileService.fetchProfileIdByFullName("John Doe")).thenReturn(Optional.empty());
        when(externalProfileService.createProfile("John Doe", 20, "Main St"))
                .thenReturn(Optional.of(new ProfileId(100L)));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StudentCode code = service.handle(command);

        // Assert
        assertNotNull(code);
        // verify that methods were called as expected
        verify(externalProfileService).fetchProfileIdByFullName("John Doe");
        verify(externalProfileService).createProfile("John Doe", 20, "Main St");
        verify(studentRepository).save(any(Student.class));
        // verify that no other interactions occurred
        verifyNoMoreInteractions(studentRepository, externalProfileService);
    }

    @Test
    @DisplayName("handle(CreateStudentCommand) should throw when student already exists by profile (AAA)")
    void handle_Create_ShouldThrow_WhenStudentExists() {
        // Arrange
        var command = new CreateStudentCommand("Jane Roe", 22, "Elm St", 9L, "2025-1");
        var profileId = new ProfileId(77L);
        when(externalProfileService.fetchProfileIdByFullName("Jane Roe")).thenReturn(Optional.of(profileId));
        Student existing = mock(Student.class);
        when(studentRepository.findByProfileId(profileId)).thenReturn(Optional.of(existing));

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Student already exists"));
        // verify that methods were called as expected
        verify(externalProfileService).fetchProfileIdByFullName("Jane Roe");
        verify(studentRepository).findByProfileId(profileId);
        // verify that no other interactions occurred
        verifyNoMoreInteractions(studentRepository, externalProfileService);
    }

    @Test
    @DisplayName("handle(CreateStudentCommand) should throw when profile cannot be created (AAA)")
    void handle_Create_ShouldThrow_WhenProfileCreationFails() {
        // Arrange
        var command = new CreateStudentCommand("Jim Poe", 19, "Oak St", 9L, "2025-1");
        when(externalProfileService.fetchProfileIdByFullName("Jim Poe")).thenReturn(Optional.empty());
        when(externalProfileService.createProfile("Jim Poe", 19, "Oak St"))
                .thenReturn(Optional.empty());

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Unable to create profile"));
        // verify that methods were called as expected
        verify(externalProfileService).fetchProfileIdByFullName("Jim Poe");
        verify(externalProfileService).createProfile("Jim Poe", 19, "Oak St");
        // verify that no other interactions occurred
        verifyNoMoreInteractions(externalProfileService);
        verifyNoInteractions(studentRepository);
    }

    @Test
    @DisplayName("handle(UpdateStudentCommand) should throw when student not found (AAA)")
    void handle_Update_ShouldThrow_WhenNotFound() {
        // Arrange
        var studentCode = new StudentCode(UUID.randomUUID().toString());
        var command = new UpdateStudentCommand(studentCode, "John Doe", 21, "Main St");
        when(studentRepository.findByStudentCode(studentCode)).thenReturn(Optional.empty());

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Student not found"));
        // verify that methods were called as expected
        verify(studentRepository).findByStudentCode(studentCode);
        // verify that no other interactions occurred after the verified ones
        verifyNoMoreInteractions(studentRepository);
        verifyNoInteractions(externalProfileService);
    }

    @Test
    @DisplayName("handle(UpdateStudentCommand) should throw when name already exists for another profile (AAA)")
    void handle_Update_ShouldThrow_WhenDuplicateName() {
        // Arrange
        var studentCode = new StudentCode(UUID.randomUUID().toString());
        var command = new UpdateStudentCommand(studentCode, "Jane Roe", 23, "Elm St");
        Student student = mock(Student.class);
        when(studentRepository.findByStudentCode(studentCode)).thenReturn(Optional.of(student));
        when(student.getProfileId()).thenReturn(55L);
        when(externalProfileService.existsProfileByFullNameAndIdIsNot("Jane Roe", 55L)).thenReturn(true);

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("already exists"));
        // verify that methods were called as expected
        verify(studentRepository).findByStudentCode(studentCode);
        verify(externalProfileService).existsProfileByFullNameAndIdIsNot("Jane Roe", 55L);
        // verify that no other interactions occurred after the verified ones
        verifyNoMoreInteractions(studentRepository, externalProfileService);
    }

    @Test
    @DisplayName("handle(UpdateStudentCommand) should throw when profile update fails (AAA)")
    void handle_Update_ShouldThrow_WhenProfileUpdateFails() {
        // Arrange
        var studentCode = new StudentCode(UUID.randomUUID().toString());
        var command = new UpdateStudentCommand(studentCode, "Jim Poe", 24, "Oak St");
        Student student = mock(Student.class);
        when(studentRepository.findByStudentCode(studentCode)).thenReturn(Optional.of(student));
        when(student.getProfileId()).thenReturn(88L);
        when(externalProfileService.existsProfileByFullNameAndIdIsNot("Jim Poe", 88L)).thenReturn(false);
        when(externalProfileService.updateProfile(88L, "Jim Poe", 24, "Oak St")).thenReturn(Optional.empty());

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Unable to update profile"));
        // verify that methods were called as expected
        verify(studentRepository).findByStudentCode(studentCode);
        verify(externalProfileService).existsProfileByFullNameAndIdIsNot("Jim Poe", 88L);
        verify(externalProfileService).updateProfile(88L, "Jim Poe", 24, "Oak St");
        // verify that no other interactions occurred after the verified ones
        verifyNoMoreInteractions(studentRepository, externalProfileService);
    }

    @Test
    @DisplayName("handle(UpdateStudentCommand) should return optional when successful (AAA)")
    void handle_Update_ShouldReturnOptional_WhenSuccessful() {
        // Arrange
        var studentCode = new StudentCode(UUID.randomUUID().toString());
        var command = new UpdateStudentCommand(studentCode, "John Updated", 25, "Pine St");
        Student student = mock(Student.class);
        when(studentRepository.findByStudentCode(studentCode)).thenReturn(Optional.of(student));
        when(student.getProfileId()).thenReturn(123L);
        when(externalProfileService.existsProfileByFullNameAndIdIsNot("John Updated", 123L)).thenReturn(false);
        when(externalProfileService.updateProfile(123L, "John Updated", 25, "Pine St"))
                .thenReturn(Optional.of(new ProfileId(123L)));

        // Act
        var actual = service.handle(command);

        // Assert
        assertTrue(actual.isPresent());
        assertSame(student, actual.get());
        verify(studentRepository).findByStudentCode(studentCode);
        verify(externalProfileService).existsProfileByFullNameAndIdIsNot("John Updated", 123L);
        verify(externalProfileService).updateProfile(123L, "John Updated", 25, "Pine St");
        // No repository save expected as it's commented out in impl
        verifyNoMoreInteractions(studentRepository, externalProfileService);
    }

    @Test
    @DisplayName("handle(DeleteStudentCommand) should throw when student not found (AAA)")
    void handle_Delete_ShouldThrow_WhenNotFound() {
        // Arrange
        var studentCode = new StudentCode(UUID.randomUUID().toString());
        var command = new DeleteStudentCommand(studentCode);
        when(studentRepository.existsByStudentCode(studentCode)).thenReturn(false);

        // Act + Assert
        var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
        assertTrue(ex.getMessage().contains("Student not found"));
        // verify that methods were called as expected
        verify(studentRepository).existsByStudentCode(studentCode);
        // verify that no other interactions occurred after the verified ones
        verifyNoMoreInteractions(studentRepository);
        verifyNoInteractions(externalProfileService);
    }

    @Test
    @DisplayName("handle(DeleteStudentCommand) should delete profile and entity (AAA)")
    void handle_Delete_ShouldDelete_WhenExists() {
        // Arrange
        var studentCode = new StudentCode(UUID.randomUUID().toString());
        var command = new DeleteStudentCommand(studentCode);
        when(studentRepository.existsByStudentCode(studentCode)).thenReturn(true);
        Student student = mock(Student.class);
        when(student.getProfileId()).thenReturn(500L);
        when(student.getId()).thenReturn(900L);
        when(studentRepository.findByStudentCode(studentCode)).thenReturn(Optional.of(student));

        // Act
        service.handle(command);

        // Assert
        verify(studentRepository).existsByStudentCode(studentCode);
        verify(studentRepository).findByStudentCode(studentCode);
        verify(externalProfileService).deleteProfile(500L);
        verify(studentRepository).deleteById(900L);
        verifyNoMoreInteractions(studentRepository, externalProfileService);
    }
}
