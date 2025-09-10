package pe.edu.upc.center.platform.iam.application.internal.eventhandlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import pe.edu.upc.center.platform.iam.domain.model.commands.SeedRolesCommand;
import pe.edu.upc.center.platform.iam.domain.services.RoleCommandService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationReadyEventHandlerTest {

  @Mock private RoleCommandService roleCommandService;
  @InjectMocks private ApplicationReadyEventHandler handler;

  @Test
  @DisplayName("on(ApplicationReadyEvent) should delegate seeding to RoleCommandService (AAA)")
  void on_ShouldDelegateSeeding() {
    // Arrange
    ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
    when(context.getId()).thenReturn("iam-app");
    ApplicationReadyEvent event = mock(ApplicationReadyEvent.class);
    when(event.getApplicationContext()).thenReturn(context);

    // Act
    handler.on(event);

    // Assert
    verify(event).getApplicationContext();
    verify(context).getId();
    verify(roleCommandService).handle(any(SeedRolesCommand.class));
    verifyNoMoreInteractions(roleCommandService, event, context);
  }
}
