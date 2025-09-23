package pe.edu.upc.center.platform.learning.application.internal.outboundservices.acl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.aggregates.Student;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.ProfileId;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.StudentCode;
import pe.edu.upc.center.platform.learning.interfaces.rest.resources.StudentResource;
import pe.edu.upc.center.platform.profiles.interfaces.acl.ProfilesContextFacade;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalProfileServiceTest {

  @Mock
  private ProfilesContextFacade profilesContextFacade;

  @InjectMocks
  private ExternalProfileService service;

  @Test
  @DisplayName("fetchProfileIdByFullName returns empty when facade returns 0L (AAA)")
  void fetchProfileIdByFullName_ReturnsEmpty_WhenZero() {
    // Arrange
    when(profilesContextFacade.fetchProfileIdByFullName("John Doe")).thenReturn(0L);

    // Act
    Optional<ProfileId> result = service.fetchProfileIdByFullName("John Doe");

    // Assert
    assertTrue(result.isEmpty());
    verify(profilesContextFacade).fetchProfileIdByFullName("John Doe");
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("fetchProfileIdByFullName returns ProfileId when facade returns id (AAA)")
  void fetchProfileIdByFullName_ReturnsProfileId_WhenPositive() {
    // Arrange
    when(profilesContextFacade.fetchProfileIdByFullName("Jane Doe")).thenReturn(42L);

    // Act
    Optional<ProfileId> result = service.fetchProfileIdByFullName("Jane Doe");

    // Assert
    assertTrue(result.isPresent());
    assertEquals(42L, result.get().profileId());
    verify(profilesContextFacade).fetchProfileIdByFullName("Jane Doe");
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("existsProfileByFullNameAndIdIsNot delegates to facade (AAA)")
  void existsProfileByFullNameAndIdIsNot_Delegates() {
    // Arrange
    when(profilesContextFacade.existsProfileByFullNameAndIdIsNot("Jane", 5L)).thenReturn(true);

    // Act
    boolean exists = service.existsProfileByFullNameAndIdIsNot("Jane", 5L);

    // Assert
    assertTrue(exists);
    verify(profilesContextFacade).existsProfileByFullNameAndIdIsNot("Jane", 5L);
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("createProfile returns empty when facade returns 0L (AAA)")
  void createProfile_ReturnsEmpty_WhenZero() {
    // Arrange
    when(profilesContextFacade.createProfile("John", 20, "Main St")).thenReturn(0L);

    // Act
    Optional<ProfileId> result = service.createProfile("John", 20, "Main St");

    // Assert
    assertTrue(result.isEmpty());
    verify(profilesContextFacade).createProfile("John", 20, "Main St");
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("createProfile returns ProfileId when facade returns id (AAA)")
  void createProfile_ReturnsProfileId_WhenPositive() {
    // Arrange
    when(profilesContextFacade.createProfile("Jane", 22, "Oak Ave")).thenReturn(7L);

    // Act
    Optional<ProfileId> result = service.createProfile("Jane", 22, "Oak Ave");

    // Assert
    assertTrue(result.isPresent());
    assertEquals(7L, result.get().profileId());
    verify(profilesContextFacade).createProfile("Jane", 22, "Oak Ave");
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("updateProfile returns empty when facade returns 0L (AAA)")
  void updateProfile_ReturnsEmpty_WhenZero() {
    // Arrange
    when(profilesContextFacade.updateProfile(9L, "John", 21, "Pine Rd")).thenReturn(0L);

    // Act
    Optional<ProfileId> result = service.updateProfile(9L, "John", 21, "Pine Rd");

    // Assert
    assertTrue(result.isEmpty());
    verify(profilesContextFacade).updateProfile(9L, "John", 21, "Pine Rd");
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("updateProfile returns ProfileId when facade returns id (AAA)")
  void updateProfile_ReturnsProfileId_WhenPositive() {
    // Arrange
    when(profilesContextFacade.updateProfile(9L, "Jane", 23, "Elm St")).thenReturn(11L);

    // Act
    Optional<ProfileId> result = service.updateProfile(9L, "Jane", 23, "Elm St");

    // Assert
    assertTrue(result.isPresent());
    assertEquals(11L, result.get().profileId());
    verify(profilesContextFacade).updateProfile(9L, "Jane", 23, "Elm St");
    verifyNoMoreInteractions(profilesContextFacade);
  }

  @Test
  @DisplayName("deleteProfile delegates to facade (AAA)")
  void deleteProfile_Delegates() {
    // Arrange
    doNothing().when(profilesContextFacade).deleteProfile(15L);

    // Act
    service.deleteProfile(15L);

    // Assert
    verify(profilesContextFacade).deleteProfile(15L);
    verifyNoMoreInteractions(profilesContextFacade);
  }

  // Helper interface to mock the profile resource returned by profilesContextFacade.fetchProfileById
  interface ProfileResourceView {
    String fullName();
    int age();
    String street();
  }

  @Test
  @DisplayName("fetchStudentResourceFromProfileId returns empty when profile not found (AAA)")
  void fetchStudentResourceFromProfileId_ReturnsEmpty_WhenProfileMissing() {
    // Arrange
    var student = mock(Student.class);
    when(student.getProfileId()).thenReturn(100L);
    when(profilesContextFacade.fetchProfileById(100L)).thenReturn(Optional.empty());

    // Act
    Optional<StudentResource> result = service.fetchStudentResourceFromProfileId(student);

    // Assert
    assertTrue(result.isEmpty());
    verify(student).getProfileId();
    verify(profilesContextFacade).fetchProfileById(100L);
    verifyNoMoreInteractions(profilesContextFacade, student);
  }

  @Test
  @DisplayName("fetchStudentResourceFromProfileId returns StudentResource when profile found (AAA)")
  void fetchStudentResourceFromProfileId_ReturnsStudentResource_WhenFound() {
    // Arrange
    var student = mock(Student.class);
    var code = new StudentCode(UUID.randomUUID().toString());
    when(student.getStudentCode()).thenReturn(code);
    when(student.getProfileId()).thenReturn(200L);
    when(student.getProgramId()).thenReturn(300L);
    when(student.getStartPeriod()).thenReturn("202520");

    ProfileResourceView profile = mock(ProfileResourceView.class);

    // Cast the mock to Object so Optional.of works, then service will call methods via reflection of its compile-time type.
    // Since ExternalProfileService calls profileResource.get().fullName(), we need the raw type to have that method.
    // Our ProfileResourceView provides it, and Mockito mock will handle it.
    when(profilesContextFacade.fetchProfileById(200L)).thenReturn(Optional.of(
            ProfileResource.class.cast(
                mock(ProfileResource.class,
                     withSettings().defaultAnswer(invocation -> {
                       switch (invocation.getMethod().getName()) {
                         case "fullName": return "Jane Smith";
                         case "age": return 25;
                         case "street": return "Sunset Blvd";
                         default: return RETURNS_DEFAULTS.answer(invocation);
                       }
                     })
                )
            )
        ));

    // Act
    Optional<StudentResource> result = service.fetchStudentResourceFromProfileId(student);

    // Assert
    assertTrue(result.isPresent());
    var sr = result.get();
    assertEquals(code.studentCode(), sr.id());
    assertEquals("Jane Smith", sr.name());
    assertEquals(25, sr.age());
    assertEquals("Sunset Blvd", sr.address());
    assertEquals(300L, sr.programId());
    assertEquals("202520", sr.startPeriod());

    verify(student).getStudentCode();
    verify(student).getProfileId();
    verify(student).getProgramId();
    verify(student).getStartPeriod();
    verify(profilesContextFacade).fetchProfileById(200L);
    verifyNoMoreInteractions(profilesContextFacade, student);
  }
}
