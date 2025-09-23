package pe.edu.upc.center.platform.iam.application.internal.commandservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.iam.domain.model.entities.Role;
import pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.center.platform.iam.domain.model.commands.SeedRolesCommand;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleCommandServiceImplTest {

  @Mock private RoleRepository roleRepository;
  @InjectMocks private RoleCommandServiceImpl service;

  @Test
  @DisplayName("handle(SeedRolesCommand) seeds all missing roles (AAA)")
  void handle_ShouldSeed_AllMissingRoles() {
    // Arrange
    for (Roles r : Roles.values()) {
      when(roleRepository.existsByName(r)).thenReturn(false);
      //when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    // Act
    service.handle(new SeedRolesCommand());

    // Assert
    for (Roles r : Roles.values()) {
      verify(roleRepository).existsByName(r);
      verify(roleRepository).save(argThat(role -> role != null && role.getName() == r));
    }
    verifyNoMoreInteractions(roleRepository);
  }

  @Test
  @DisplayName("handle(SeedRolesCommand) skips existing roles (AAA)")
  void handle_ShouldSkip_WhenRoleExists() {
    // Arrange
    Roles[] vals = Roles.values();
    when(roleRepository.existsByName(vals[0])).thenReturn(true);
    for (int i = 1; i < vals.length; i++) {
      when(roleRepository.existsByName(vals[i])).thenReturn(false);
    }
    when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

    // Act
    service.handle(new SeedRolesCommand());

    // Assert
    verify(roleRepository).existsByName(vals[0]);
    verify(roleRepository, never()).save(argThat(role -> role != null && role.getName() == vals[0]));
    for (int i = 1; i < vals.length; i++) {
      Roles r = vals[i];
      verify(roleRepository).existsByName(r);
      verify(roleRepository).save(argThat(role -> role != null && role.getName() == r));
    }
    verifyNoMoreInteractions(roleRepository);
  }
}
