package pe.edu.upc.center.platform.profiles.application.internal.commandservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.commands.CreateProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.DeleteProfileCommand;
import pe.edu.upc.center.platform.profiles.domain.model.commands.UpdateProfileCommand;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileCommandServiceImplTest {

  @Mock
  private ProfileRepository profileRepository;

  @InjectMocks
  private ProfileCommandServiceImpl service;

  @Test
  @DisplayName("handle(CreateProfileCommand) should create profile when fullName not exists (AAA)")
  void handle_Create_ShouldCreate_WhenNotExists() {
    // Arrange
    var command = new CreateProfileCommand("John Doe", 20, "Main St");
    when(profileRepository.existsByFullName("John Doe")).thenReturn(false);
    when(profileRepository.save(any(Profile.class))).thenAnswer(inv -> inv.getArgument(0));

    // Act
    Long id = service.handle(command);

    // Assert
    assertNull(id, "Expected null id because repository is mocked and entity id is not set");
    verify(profileRepository).existsByFullName("John Doe");
    verify(profileRepository).save(any(Profile.class));
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(CreateProfileCommand) should throw when fullName already exists (AAA)")
  void handle_Create_ShouldThrow_WhenDuplicateFullName() {
    // Arrange
    var command = new CreateProfileCommand("John Doe", 20, "Main St");
    when(profileRepository.existsByFullName("John Doe")).thenReturn(true);

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("already exists"));

    verify(profileRepository).existsByFullName("John Doe");
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(CreateProfileCommand) should wrap repository exception (AAA)")
  void handle_Create_ShouldWrap_WhenRepoThrows() {
    // Arrange
    var command = new CreateProfileCommand("John Doe", 20, "Main St");
    when(profileRepository.existsByFullName("John Doe")).thenReturn(false);
    when(profileRepository.save(any(Profile.class))).thenThrow(new RuntimeException("db down"));

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("Error while saving profile"));

    verify(profileRepository).existsByFullName("John Doe");
    verify(profileRepository).save(any(Profile.class));
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(UpdateProfileCommand) should update when valid (AAA)")
  void handle_Update_ShouldUpdate_WhenValid() {
    // Arrange
    var command = new UpdateProfileCommand(5L, "Jane Doe", 22, "Oak Ave");
    var existing = mock(Profile.class);
    when(profileRepository.existsByFullNameAndIdIsNot("Jane Doe", 5L)).thenReturn(false);
    when(profileRepository.existsById(5L)).thenReturn(true);
    when(profileRepository.findById(5L)).thenReturn(Optional.of(existing));
    when(profileRepository.save(existing)).thenReturn(existing);

    // Act
    var result = service.handle(command);

    // Assert
    assertTrue(result.isPresent());
    assertSame(existing, result.get());
    verify(profileRepository).existsByFullNameAndIdIsNot("Jane Doe", 5L);
    verify(profileRepository).existsById(5L);
    verify(profileRepository).findById(5L);
    verify(existing).updateInformation("Jane Doe", 22, "Oak Ave");
    verify(profileRepository).save(existing);
    verifyNoMoreInteractions(profileRepository, existing);
  }

  @Test
  @DisplayName("handle(UpdateProfileCommand) should throw when fullName conflict (AAA)")
  void handle_Update_ShouldThrow_WhenFullNameConflict() {
    // Arrange
    var command = new UpdateProfileCommand(5L, "Jane Doe", 22, "Oak Ave");
    when(profileRepository.existsByFullNameAndIdIsNot("Jane Doe", 5L)).thenReturn(true);

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("already exists"));

    verify(profileRepository).existsByFullNameAndIdIsNot("Jane Doe", 5L);
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(UpdateProfileCommand) should throw when id not exists (AAA)")
  void handle_Update_ShouldThrow_WhenIdNotExists() {
    // Arrange
    var command = new UpdateProfileCommand(5L, "Jane Doe", 22, "Oak Ave");
    when(profileRepository.existsByFullNameAndIdIsNot("Jane Doe", 5L)).thenReturn(false);
    when(profileRepository.existsById(5L)).thenReturn(false);

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("does not exist"));

    verify(profileRepository).existsByFullNameAndIdIsNot("Jane Doe", 5L);
    verify(profileRepository).existsById(5L);
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(UpdateProfileCommand) should wrap repository exception (AAA)")
  void handle_Update_ShouldWrap_WhenRepoThrows() {
    // Arrange
    var command = new UpdateProfileCommand(5L, "Jane Doe", 22, "Oak Ave");
    var existing = mock(Profile.class);
    when(profileRepository.existsByFullNameAndIdIsNot("Jane Doe", 5L)).thenReturn(false);
    when(profileRepository.existsById(5L)).thenReturn(true);
    when(profileRepository.findById(5L)).thenReturn(Optional.of(existing));
    when(profileRepository.save(existing)).thenThrow(new RuntimeException("db down"));

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("Error while updating profile"));

    verify(profileRepository).existsByFullNameAndIdIsNot("Jane Doe", 5L);
    verify(profileRepository).existsById(5L);
    verify(profileRepository).findById(5L);
    verify(existing).updateInformation("Jane Doe", 22, "Oak Ave");
    verify(profileRepository).save(existing);
    verifyNoMoreInteractions(profileRepository, existing);
  }

  @Test
  @DisplayName("handle(DeleteProfileCommand) should delete when id exists (AAA)")
  void handle_Delete_ShouldDelete_WhenIdExists() {
    // Arrange
    var command = new DeleteProfileCommand(7L);
    when(profileRepository.existsById(7L)).thenReturn(true);
    doNothing().when(profileRepository).deleteById(7L);

    // Act
    service.handle(command);

    // Assert
    verify(profileRepository).existsById(7L);
    verify(profileRepository).deleteById(7L);
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(DeleteProfileCommand) should throw when id does not exist (AAA)")
  void handle_Delete_ShouldThrow_WhenIdNotExists() {
    // Arrange
    var command = new DeleteProfileCommand(7L);
    when(profileRepository.existsById(7L)).thenReturn(false);

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("does not exist"));

    verify(profileRepository).existsById(7L);
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(DeleteProfileCommand) should wrap repository exception (AAA)")
  void handle_Delete_ShouldWrap_WhenRepoThrows() {
    // Arrange
    var command = new DeleteProfileCommand(7L);
    when(profileRepository.existsById(7L)).thenReturn(true);
    doThrow(new RuntimeException("db down")).when(profileRepository).deleteById(7L);

    // Act + Assert
    var ex = assertThrows(IllegalArgumentException.class, () -> service.handle(command));
    assertTrue(ex.getMessage().contains("Error while deleting profile"));

    verify(profileRepository).existsById(7L);
    verify(profileRepository).deleteById(7L);
    verifyNoMoreInteractions(profileRepository);
  }
}
