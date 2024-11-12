package pe.edu.upc.center.platform.learning.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.platform.learning.domain.model.commands.SeedEnrollmentStatusesCommand;
import pe.edu.upc.center.platform.learning.domain.model.entities.EnrollmentStatus;
import pe.edu.upc.center.platform.learning.domain.model.valueobjects.EnrollmentStatuses;
import pe.edu.upc.center.platform.learning.domain.services.EnrollmentStatusCommandService;
import pe.edu.upc.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentStatusRepository;

import java.util.Arrays;

@Service
public class EnrollmentStatusCommandServiceImpl implements EnrollmentStatusCommandService {

  private final EnrollmentStatusRepository enrollmentStatusRepository;

  public EnrollmentStatusCommandServiceImpl(EnrollmentStatusRepository enrollmentStatusRepository) {
    this.enrollmentStatusRepository = enrollmentStatusRepository;
  }

  @Override
  public void handle(SeedEnrollmentStatusesCommand command) {
    Arrays.stream(EnrollmentStatuses.values())
        .forEach(enrollmentStatus -> {
          if(!enrollmentStatusRepository.existsByName(enrollmentStatus)) {
            enrollmentStatusRepository.save(new EnrollmentStatus(EnrollmentStatuses.valueOf(enrollmentStatus.name())));
          }
        } );
  }
}
