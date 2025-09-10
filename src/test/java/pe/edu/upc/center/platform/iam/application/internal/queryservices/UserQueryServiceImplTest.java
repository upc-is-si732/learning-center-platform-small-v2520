package pe.edu.upc.center.platform.iam.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.iam.domain.model.aggregates.User;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetAllUsersQuery;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetUserByIdQuery;
import pe.edu.upc.center.platform.iam.domain.model.queries.GetUserByUsernameQuery;
import pe.edu.upc.center.platform.iam.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceImplTest {

  @Mock private UserRepository userRepository;
  @InjectMocks private UserQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllUsersQuery) returns list from repository (AAA)")
  void handle_GetAll_ShouldReturnList() {
    // Arrange
    var query = new GetAllUsersQuery();
    User a = mock(User.class);
    User b = mock(User.class);
    when(userRepository.findAll()).thenReturn(List.of(a, b));

    // Act
    var actual = service.handle(query);

    // Assert
    assertEquals(2, actual.size());
    assertSame(a, actual.get(0));
    assertSame(b, actual.get(1));
    verify(userRepository).findAll();
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  @DisplayName("handle(GetUserByIdQuery) returns Optional from repository (AAA)")
  void handle_GetById_ShouldReturnOptional() {
    // Arrange
    var query = new GetUserByIdQuery(10L);
    User expected = mock(User.class);
    when(userRepository.findById(10L)).thenReturn(Optional.of(expected));

    // Act
    Optional<User> actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(userRepository).findById(10L);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  @DisplayName("handle(GetUserByUsernameQuery) returns Optional from repository (AAA)")
  void handle_GetByUsername_ShouldReturnOptional() {
    // Arrange
    var query = new GetUserByUsernameQuery("alice");
    User expected = mock(User.class);
    when(userRepository.findByUsername("alice")).thenReturn(Optional.of(expected));

    // Act
    Optional<User> actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(userRepository).findByUsername("alice");
    verifyNoMoreInteractions(userRepository);
  }
}
