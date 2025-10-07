package pe.edu.upc.center.platform.learning.application.internal.eventhandlers;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.commands.SeedEnrollmentStatusesCommand;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentStatusCommandService;

/**
 * Event handler that listens for the ApplicationReadyEvent to seed enrollment statuses if needed.
 */
@Service
public class LearningReadyEventHandler {

  private final EnrollmentStatusCommandService enrollmentStatusCommandService;
  private static final Logger LOGGER = LoggerFactory.getLogger(LearningReadyEventHandler.class);

  /**
   * Constructor for LearningReadyEventHandler.
   *
   * @param enrollmentStatusCommandService the service to handle enrollment status commands
   */
  public LearningReadyEventHandler(EnrollmentStatusCommandService enrollmentStatusCommandService) {
    this.enrollmentStatusCommandService = enrollmentStatusCommandService;
  }

  /**
   * Handles the ApplicationReadyEvent to seed enrollment statuses if needed.
   *
   * @param event the ApplicationReadyEvent
   */
  @EventListener
  public void on(ApplicationReadyEvent event) {
    var applicationName = event.getApplicationContext().getId();
    LOGGER.info("Starting to verify if enrollment statuses seeding is needed for {} at {}",
        applicationName, currentTimestamp());

    enrollmentStatusCommandService.handle(new SeedEnrollmentStatusesCommand());
    LOGGER.info("Enrollment statuses seeding verification finished for {} at {}",
        applicationName, currentTimestamp());
  }

  private Timestamp currentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }
}
