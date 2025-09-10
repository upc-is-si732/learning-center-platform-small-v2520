package pe.edu.upc.center.platform.profiles.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetAllProfilesQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByAgeQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByFullNameQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileQueryServiceImplTest {

  @Mock
  private ProfileRepository profileRepository;

  @InjectMocks
  private ProfileQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllProfilesQuery) should return list from repository (AAA)")
  void handle_GetAll_ShouldReturnList() {
    // Arrange
    var query = new GetAllProfilesQuery();
    Profile a = mock(Profile.class);
    Profile b = mock(Profile.class);
    when(profileRepository.findAll()).thenReturn(List.of(a, b));

    // Act
    var result = service.handle(query);

    // Assert
    assertEquals(2, result.size());
    assertSame(a, result.get(0));
    assertSame(b, result.get(1));
    verify(profileRepository).findAll();
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(GetProfileByIdQuery) should return Optional from repository (AAA)")
  void handle_GetById_ShouldReturnOptional() {
    // Arrange
    var query = new GetProfileByIdQuery(10L);
    Profile expected = mock(Profile.class);
    when(profileRepository.findById(10L)).thenReturn(Optional.of(expected));

    // Act
    Optional<Profile> actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(profileRepository).findById(10L);
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(GetProfileByFullNameQuery) should return Optional from repository (AAA)")
  void handle_GetByFullName_ShouldReturnOptional() {
    // Arrange
    var query = new GetProfileByFullNameQuery("John Doe");
    Profile expected = mock(Profile.class);
    when(profileRepository.findByFullName("John Doe")).thenReturn(Optional.of(expected));

    // Act
    Optional<Profile> actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(profileRepository).findByFullName("John Doe");
    verifyNoMoreInteractions(profileRepository);
  }

  @Test
  @DisplayName("handle(GetProfileByAgeQuery) should return list from repository (AAA)")
  void handle_GetByAge_ShouldReturnList() {
    // Arrange
    var query = new GetProfileByAgeQuery(21);
    Profile a = mock(Profile.class);
    when(profileRepository.findByAge(21)).thenReturn(List.of(a));

    // Act
    var result = service.handle(query);

    // Assert
    assertEquals(1, result.size());
    assertSame(a, result.get(0));
    verify(profileRepository).findByAge(21);
    verifyNoMoreInteractions(profileRepository);
  }
}
