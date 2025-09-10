package pe.edu.upc.center.platform.learning.application.internal.queryservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetAllEnrollmentStatusesQuery;
import pe.edu.upc.center.platform.learning.domain.model.queries.GetEnrollmentStatusByNameQuery;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentStatusRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentStatusQueryServiceImplTest {

  @Mock
  private EnrollmentStatusRepository enrollmentStatusRepository;

  @InjectMocks
  private EnrollmentStatusQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllEnrollmentStatusesQuery) should return list from repository (AAA)")
  void handle_GetAll_ShouldReturnList() {
    // Arrange
    var query = new GetAllEnrollmentStatusesQuery();
    EnrollmentStatus a = mock(EnrollmentStatus.class);
    EnrollmentStatus b = mock(EnrollmentStatus.class);
    when(enrollmentStatusRepository.findAll()).thenReturn(List.of(a, b));

    // Act
    var actual = service.handle(query);

    // Assert
    assertEquals(2, actual.size());
    assertSame(a, actual.get(0));
    assertSame(b, actual.get(1));
    verify(enrollmentStatusRepository, times(1)).findAll();
    verifyNoMoreInteractions(enrollmentStatusRepository);
  }

  @Test
  @DisplayName("handle(GetEnrollmentStatusByNameQuery) should return Optional when found (AAA)")
  void handle_GetByName_ShouldReturnOptional_WhenFound() {
    // Arrange
    var name = EnrollmentStatuses.REGULAR;
    var query = new GetEnrollmentStatusByNameQuery(name);
    EnrollmentStatus expected = mock(EnrollmentStatus.class);
    when(enrollmentStatusRepository.findByName(name)).thenReturn(Optional.of(expected));

    // Act
    Optional<EnrollmentStatus> actual = service.handle(query);

    // Assert
    assertTrue(actual.isPresent());
    assertSame(expected, actual.get());
    verify(enrollmentStatusRepository, times(1)).findByName(name);
    verifyNoMoreInteractions(enrollmentStatusRepository);
  }

  @Test
  @DisplayName("handle(GetEnrollmentStatusByNameQuery) should return empty when not found (AAA)")
  void handle_GetByName_ShouldReturnEmpty_WhenNotFound() {
    // Arrange
    var name = EnrollmentStatuses.RISK;
    var query = new GetEnrollmentStatusByNameQuery(name);
    when(enrollmentStatusRepository.findByName(name)).thenReturn(Optional.empty());

    // Act
    Optional<EnrollmentStatus> actual = service.handle(query);

    // Assert
    assertTrue(actual.isEmpty());
    verify(enrollmentStatusRepository, times(1)).findByName(name);
    verifyNoMoreInteractions(enrollmentStatusRepository);
  }
}
