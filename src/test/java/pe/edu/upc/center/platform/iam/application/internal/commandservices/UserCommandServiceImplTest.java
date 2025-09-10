package pe.edu.upc.center.platform.iam.application.internal.commandservices;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.center.platform.iam.application.internal.outboundservices.tokens.TokenService;
import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.center.platform.iam.domain.model.commands.SignInCommand;
import pe.edu.upc.center.platform.iam.domain.model.commands.SignUpCommand;
import pe.edu.upc.center.platform.iam.domain.model.valueobjects.Roles;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private HashingService hashingService;
  @Mock private TokenService tokenService;
  @Mock private RoleRepository roleRepository;

  @InjectMocks private UserCommandServiceImpl service;

  @Test
  @DisplayName("handle(SignInCommand) should return user and token when credentials valid (AAA)")
  void handle_SignIn_ShouldReturnPair_WhenValid() {
    // Arrange
    var cmd = new SignInCommand("alice", "secret");
    var user = mock(User.class);
    when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
    when(user.getPassword()).thenReturn("hashed");
    when(hashingService.matches("secret", "hashed")).thenReturn(true);
    when(user.getUsername()).thenReturn("alice");
    when(tokenService.generateToken("alice")).thenReturn("jwt");

    // Act
    Optional<ImmutablePair<User, String>> result = service.handle(cmd);

    // Assert
    assertTrue(result.isPresent());
    assertSame(user, result.get().left);
    assertEquals("jwt", result.get().right);
    verify(userRepository).findByUsername("alice");
    verify(hashingService).matches("secret", "hashed");
    verify(tokenService).generateToken("alice");
    verifyNoMoreInteractions(userRepository, hashingService, tokenService);
    verifyNoInteractions(roleRepository);
  }

  @Test
  @DisplayName("handle(SignInCommand) should throw when user not found (AAA)")
  void handle_SignIn_ShouldThrow_WhenUserMissing() {
    // Arrange
    var cmd = new SignInCommand("bob", "x");
    when(userRepository.findByUsername("bob")).thenReturn(Optional.empty());

    // Act + Assert
    assertThrows(RuntimeException.class, () -> service.handle(cmd));

    verify(userRepository).findByUsername("bob");
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(hashingService, tokenService, roleRepository);
  }

  @Test
  @DisplayName("handle(SignInCommand) should throw when password invalid (AAA)")
  void handle_SignIn_ShouldThrow_WhenPasswordInvalid() {
    // Arrange
    var cmd = new SignInCommand("alice", "wrong");
    var user = mock(User.class);
    when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
    when(user.getPassword()).thenReturn("hashed");
    when(hashingService.matches("wrong", "hashed")).thenReturn(false);

    // Act + Assert
    assertThrows(RuntimeException.class, () -> service.handle(cmd));

    verify(userRepository).findByUsername("alice");
    verify(hashingService).matches("wrong", "hashed");
    verifyNoMoreInteractions(userRepository, hashingService);
    verifyNoInteractions(tokenService, roleRepository);
  }

  @Test
  @DisplayName("handle(SignUpCommand) should create user when username not exists and roles valid (AAA)")
  void handle_SignUp_ShouldCreate_WhenValid() {
    // Arrange
    var requestedRoleUser = mock(pe.edu.upc.center.platform.iam.domain.model.entities.Role.class);
    var requestedRoleStudent = mock(pe.edu.upc.center.platform.iam.domain.model.entities.Role.class);
    when(requestedRoleUser.getName()).thenReturn(Roles.ROLE_USER);
    when(requestedRoleStudent.getName()).thenReturn(Roles.ROLE_STUDENT);
    var cmd = new SignUpCommand("charlie", "pwd", List.of(requestedRoleUser, requestedRoleStudent));

    when(userRepository.existsByUsername("charlie")).thenReturn(false);
    var roleUser = pe.edu.upc.center.platform.iam.domain.model.entities.Role.class.cast(
        mock(pe.edu.upc.center.platform.iam.domain.model.entities.Role.class));
    var roleStudent = pe.edu.upc.center.platform.iam.domain.model.entities.Role.class.cast(
        mock(pe.edu.upc.center.platform.iam.domain.model.entities.Role.class));
    when(roleRepository.findByName(Roles.ROLE_USER)).thenReturn(Optional.of(roleUser));
    when(roleRepository.findByName(Roles.ROLE_STUDENT)).thenReturn(Optional.of(roleStudent));
    when(hashingService.encode("pwd")).thenReturn("hashedPwd");

    // userRepository.save returns the same entity
    doAnswer(inv -> null).when(userRepository).save(any(User.class));
    when(userRepository.findByUsername("charlie")).thenReturn(Optional.of(mock(User.class)));

    // Act
    Optional<User> result = service.handle(cmd);

    // Assert
    assertTrue(result.isPresent());
    verify(userRepository).existsByUsername("charlie");
    verify(roleRepository).findByName(Roles.ROLE_USER);
    verify(roleRepository).findByName(Roles.ROLE_STUDENT);
    verify(hashingService).encode("pwd");
    verify(userRepository).save(any(User.class));
    verify(userRepository).findByUsername("charlie");
    verifyNoMoreInteractions(userRepository, roleRepository, hashingService);
    verifyNoInteractions(tokenService);
  }

  @Test
  @DisplayName("handle(SignUpCommand) should throw when username exists (AAA)")
  void handle_SignUp_ShouldThrow_WhenUsernameExists() {
    // Arrange
    var cmd = new SignUpCommand("alice", "pwd", List.of());
    when(userRepository.existsByUsername("alice")).thenReturn(true);

    // Act + Assert
    assertThrows(RuntimeException.class, () -> service.handle(cmd));

    verify(userRepository).existsByUsername("alice");
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(roleRepository, hashingService, tokenService);
  }

  @Test
  @DisplayName("handle(SignUpCommand) should throw when some role name not found (AAA)")
  void handle_SignUp_ShouldThrow_WhenRoleNotFound() {
    // Arrange
    var requestedRoleAdmin = mock(pe.edu.upc.center.platform.iam.domain.model.entities.Role.class);
    when(requestedRoleAdmin.getName()).thenReturn(Roles.ROLE_ADMIN);
    var cmd = new SignUpCommand("new", "pwd", List.of(requestedRoleAdmin));
    when(userRepository.existsByUsername("new")).thenReturn(false);
    when(roleRepository.findByName(Roles.ROLE_ADMIN)).thenReturn(Optional.empty());

    // Act + Assert
    assertThrows(RuntimeException.class, () -> service.handle(cmd));

    verify(userRepository).existsByUsername("new");
    verify(roleRepository).findByName(Roles.ROLE_ADMIN);
    verifyNoMoreInteractions(userRepository, roleRepository);
    verifyNoInteractions(hashingService, tokenService);
  }
}
