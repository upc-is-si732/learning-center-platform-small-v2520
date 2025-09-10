package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.upc.center.platform.learning.domain.model.commands.SeedEnrollmentStatusesCommand;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentStatusRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentStatusCommandServiceImplTest {

  @Mock
  private EnrollmentStatusRepository enrollmentStatusRepository;

  @InjectMocks
  private EnrollmentStatusCommandServiceImpl service;

  @Test
  @DisplayName("handle(SeedEnrollmentStatusesCommand) seeds all missing statuses (AAA)")
  void handle_ShouldSeed_AllMissingStatuses() {
    // Arrange
    for (EnrollmentStatuses status : EnrollmentStatuses.values()) {
      when(enrollmentStatusRepository.existsByName(status)).thenReturn(false);
      // save returns same entity
      when(enrollmentStatusRepository.save(any(EnrollmentStatus.class)))
          .thenAnswer(invocation -> invocation.getArgument(0));
    }

    // Act
    service.handle(new SeedEnrollmentStatusesCommand());

    // Assert
    for (EnrollmentStatuses status : EnrollmentStatuses.values()) {
      verify(enrollmentStatusRepository).existsByName(status);
      verify(enrollmentStatusRepository)
          .save(argThat(es -> es != null && es.getName() == status));
    }
    verifyNoMoreInteractions(enrollmentStatusRepository);
  }

  @Test
  @DisplayName("handle(SeedEnrollmentStatusesCommand) skips existing statuses (AAA)")
  void handle_ShouldSkip_WhenStatusExists() {
    // Arrange
    EnrollmentStatuses[] values = EnrollmentStatuses.values();
    // mark first as existing, rest missing
    when(enrollmentStatusRepository.existsByName(values[0])).thenReturn(true);
    for (int i = 1; i < values.length; i++) {
      when(enrollmentStatusRepository.existsByName(values[i])).thenReturn(false);
    }
    when(enrollmentStatusRepository.save(any(EnrollmentStatus.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    service.handle(new SeedEnrollmentStatusesCommand());

    // Assert
    verify(enrollmentStatusRepository).existsByName(values[0]);
    verify(enrollmentStatusRepository, never()).save(argThat(es -> es != null && es.getName() == values[0]));
    for (int i = 1; i < values.length; i++) {
      var status = values[i];
      verify(enrollmentStatusRepository).existsByName(status);
      verify(enrollmentStatusRepository).save(argThat(es -> es != null && es.getName() == status));
    }
    verifyNoMoreInteractions(enrollmentStatusRepository);
  }
}
