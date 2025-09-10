package pe.edu.upc.center.platform.profiles.interfaces.rest.transform;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.edu.upc.center.platform.profiles.domain.model.aggregates.Profile;
import pe.edu.upc.center.platform.profiles.interfaces.rest.resources.ProfileResource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResourceFromEntity should map Profile entity to ProfileResource (AAA)")
  void toResourceFromEntity_ShouldMap() {
    // Arrange
    Profile entity = mock(Profile.class);
    when(entity.getId()).thenReturn(10L);
    when(entity.getFullName()).thenReturn("Jane Doe");
    when(entity.getAge()).thenReturn(22);
    when(entity.getAddress()).thenReturn("Oak Ave");

    // Act
    ProfileResource resource = ProfileResourceFromEntityAssembler.toResourceFromEntity(entity);

    // Assert
    assertNotNull(resource);
    assertEquals(10L, resource.id());
    assertEquals("Jane Doe", resource.fullName());
    assertEquals(22, resource.age());
    assertEquals("Oak Ave", resource.street());

    verify(entity).getId();
    verify(entity).getFullName();
    verify(entity).getAge();
    verify(entity).getAddress();
    verifyNoMoreInteractions(entity);
  }
}
