package pe.edu.upc.center.platform.iam.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.iam.domain.model.entities.Role;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetAllRolesQuery;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetRoleByNameQuery;
import pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleQueryServiceImplTest {

  @Mock private RoleRepository roleRepository;
  @InjectMocks private RoleQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllRolesQuery) should return list from repository (AAA)")
  void handle_GetAll_ShouldReturnList() {
    // Arrange
    var query = new GetAllRolesQuery();
    Role a = mock(Role.class);
    Role b = mock(Role.class);
    when(roleRepository.findAll()).thenReturn(List.of(a, b));

    // Act
    var actual = service.handle(query);

    // Assert
    assertEquals(2, actual.size());
    assertSame(a, actual.get(0));
    assertSame(b, actual.get(1));
    verify(roleRepository).findAll();
    verifyNoMoreInteractions(roleRepository);
  }

  @Test
  @DisplayName("handle(GetRoleByNameQuery) should return Optional from repository (AAA)")
  void handle_GetByName_ShouldReturnOptional() {
    // Arrange
    var query = new GetRoleByNameQuery(Roles.ROLE_STUDENT);
    Role expected = mock(Role.class);
    when(roleRepository.findByName(Roles.ROLE_STUDENT)).thenReturn(Optional.of(expected));

    // Act
    Optional<Role> result = service.handle(query);

    // Assert
    assertTrue(result.isPresent());
    assertSame(expected, result.get());
    verify(roleRepository).findByName(Roles.ROLE_STUDENT);
    verifyNoMoreInteractions(roleRepository);
  }
}
