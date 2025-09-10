package pe.edu.upc.center.platform.learning.application.internal.eventhandlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import pe.edu.upc.center.platform.learning.domain.model.commands.SeedEnrollmentStatusesCommand;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentStatusCommandService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningReadyEventHandlerTest {

  @Mock
  private EnrollmentStatusCommandService enrollmentStatusCommandService;

  @InjectMocks
  private LearningReadyEventHandler handler;

  @Test
  @DisplayName("on(ApplicationReadyEvent) should delegate seeding to EnrollmentStatusCommandService (AAA)")
  void on_ShouldDelegateSeeding() {
    // Arrange
    ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
    when(context.getId()).thenReturn("test-app");
    ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
    when(event.getApplicationContext()).thenReturn(context);

    // Act
    handler.on(event);

    // Assert
    verify(event).getApplicationContext();
    verify(context).getId();
    verify(enrollmentStatusCommandService).handle(any(SeedEnrollmentStatusesCommand.class));
    verifyNoMoreInteractions(enrollmentStatusCommandService, event, context);
  }
}
