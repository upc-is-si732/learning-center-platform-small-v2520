package pe.edu.upc.center.platform.profiles.interfaces.acl;

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
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByFullNameQuery;
import pe.edu.upc.center.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileCommandService;
import pe.edu.upc.center.platform.profiles.domain.services.ProfileQueryService;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfilesContextFacadeTest {

  @Mock private ProfileCommandService profileCommandService;
  @Mock private ProfileQueryService profileQueryService;

  @InjectMocks private ProfilesContextFacade facade;

  @Test
  @DisplayName("fetchProfileById returns empty when query returns empty (AAA)")
  void fetchProfileById_ReturnsEmpty_WhenMissing() {
    // Arrange
    when(profileQueryService.handle(new GetProfileByIdQuery(10L))).thenReturn(Optional.empty());

    // Act
    var result = facade.fetchProfileById(10L);

    // Assert
    assertTrue(result.isEmpty());
    verify(profileQueryService).handle(new GetProfileByIdQuery(10L));
    verifyNoMoreInteractions(profileQueryService);
    verifyNoInteractions(profileCommandService);
  }

  @Test
  @DisplayName("fetchProfileById maps entity to resource when present (AAA)")
  void fetchProfileById_ReturnsResource_WhenPresent() {
    // Arrange
    var entity = mock(Profile.class);
    when(entity.getId()).thenReturn(5L);
    when(entity.getFullName()).thenReturn("Jane Doe");
    when(entity.getAge()).thenReturn(22);
    when(entity.getAddress()).thenReturn("Oak Ave");
    when(profileQueryService.handle(new GetProfileByIdQuery(5L))).thenReturn(Optional.of(entity));

    // Act
    var result = facade.fetchProfileById(5L);

    // Assert
    assertTrue(result.isPresent());
    ProfileResource resource = result.get();
    assertEquals(5L, resource.id());
    assertEquals("Jane Doe", resource.fullName());
    assertEquals(22, resource.age());
    assertEquals("Oak Ave", resource.street());

    verify(profileQueryService).handle(new GetProfileByIdQuery(5L));
    verifyNoMoreInteractions(profileQueryService);
    verifyNoInteractions(profileCommandService);
  }

  @Test
  @DisplayName("fetchProfileIdByFullName returns 0L when not found; otherwise returns entity id (AAA)")
  void fetchProfileIdByFullName_Behavior() {
    // Arrange: not found
    when(profileQueryService.handle(new GetProfileByFullNameQuery("John"))).thenReturn(Optional.empty());

    // Act
    Long missing = facade.fetchProfileIdByFullName("John");

    // Assert
    assertEquals(0L, missing);
    verify(profileQueryService).handle(new GetProfileByFullNameQuery("John"));

    // Arrange: found
    var entity = mock(Profile.class);
    when(entity.getId()).thenReturn(9L);
    when(profileQueryService.handle(new GetProfileByFullNameQuery("Jane"))).thenReturn(Optional.of(entity));

    // Act
    Long found = facade.fetchProfileIdByFullName("Jane");

    // Assert
    assertEquals(9L, found);
    verify(profileQueryService).handle(new GetProfileByFullNameQuery("Jane"));
    verifyNoMoreInteractions(profileQueryService);
    verifyNoInteractions(profileCommandService);
  }

  @Test
  @DisplayName("existsProfileByFullNameAndIdIsNot returns false when not found; true when id differs; false when same id (AAA)")
  void existsProfileByFullNameAndIdIsNot_Various() {
    // Arrange: not found
    when(profileQueryService.handle(new GetProfileByFullNameQuery("No One"))).thenReturn(Optional.empty());
    assertFalse(facade.existsProfileByFullNameAndIdIsNot("No One", 1L));
    verify(profileQueryService).handle(new GetProfileByFullNameQuery("No One"));

    // Arrange: found with same id -> false
    var same = mock(Profile.class);
    when(same.getId()).thenReturn(7L);
    when(profileQueryService.handle(new GetProfileByFullNameQuery("Same"))).thenReturn(Optional.of(same));
    assertFalse(facade.existsProfileByFullNameAndIdIsNot("Same", 7L));
    verify(profileQueryService).handle(new GetProfileByFullNameQuery("Same"));

    // Arrange: found with different id -> true
    var diff = mock(Profile.class);
    when(diff.getId()).thenReturn(8L);
    when(profileQueryService.handle(new GetProfileByFullNameQuery("Diff"))).thenReturn(Optional.of(diff));
    assertTrue(facade.existsProfileByFullNameAndIdIsNot("Diff", 7L));
    verify(profileQueryService).handle(new GetProfileByFullNameQuery("Diff"));

    verifyNoMoreInteractions(profileQueryService);
    verifyNoInteractions(profileCommandService);
  }

  @Test
  @DisplayName("createProfile returns 0L when command service returns null; otherwise returns id (AAA)")
  void createProfile_Behavior() {
    // Arrange: returns null
    when(profileCommandService.handle(any(CreateProfileCommand.class))).thenReturn(null);
    Long id0 = facade.createProfile("John", 20, "Main St");
    assertEquals(0L, id0);
    verify(profileCommandService).handle(any(CreateProfileCommand.class));

    // Arrange: returns id
    when(profileCommandService.handle(any(CreateProfileCommand.class))).thenReturn(15L);
    Long id15 = facade.createProfile("Jane", 21, "Oak Ave");
    assertEquals(15L, id15);
    verify(profileCommandService, times(2)).handle(any(CreateProfileCommand.class));

    verifyNoMoreInteractions(profileCommandService);
    verifyNoInteractions(profileQueryService);
  }

  @Test
  @DisplayName("updateProfile returns 0L when Optional.empty; otherwise returns updated id (AAA)")
  void updateProfile_Behavior() {
    // Arrange: empty
    when(profileCommandService.handle(any(UpdateProfileCommand.class))).thenReturn(Optional.empty());
    Long zero = facade.updateProfile(3L, "A", 19, "S");
    assertEquals(0L, zero);

    // Arrange: present
    var updated = mock(Profile.class);
    when(updated.getId()).thenReturn(33L);
    when(profileCommandService.handle(any(UpdateProfileCommand.class))).thenReturn(Optional.of(updated));
    Long val = facade.updateProfile(33L, "B", 25, "T");
    assertEquals(33L, val);

    verify(profileCommandService, times(2)).handle(any(UpdateProfileCommand.class));
    verifyNoMoreInteractions(profileCommandService);
    verifyNoInteractions(profileQueryService);
  }

  @Test
  @DisplayName("deleteProfile delegates to command service (AAA)")
  void deleteProfile_Delegates() {
    // Arrange
    doNothing().when(profileCommandService).handle(any(DeleteProfileCommand.class));

    // Act
    facade.deleteProfile(77L);

    // Assert
    verify(profileCommandService).handle(new DeleteProfileCommand(77L));
    verifyNoMoreInteractions(profileCommandService);
    verifyNoInteractions(profileQueryService);
  }
}
